package com.example.review.domain.members.service.implement;

import static com.example.review.global.exception.ErrorCode.*;

import com.example.review.domain.items.entity.Item;
import com.example.review.domain.items.entity.ItemState;
import com.example.review.domain.items.repository.ItemRepository;
import com.example.review.domain.members.dto.*;
import com.example.review.domain.members.entity.Cart;
import com.example.review.domain.members.entity.Member;
import com.example.review.domain.members.repository.CartRepository;
import com.example.review.domain.members.repository.MemberRepository;
import com.example.review.domain.members.service.CartService;
import com.example.review.global.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    //장바구니 생성
    @Override
    @Transactional
    public CartResponse create(CreateCartRequest cartRequest, Member member) {
        Item item = existItemCheck(cartRequest.itemId());

        // CartRequest.Option -> Cart.Option
        List<Cart.Option> options = cartRequest.optionValue().stream()
            .map(option -> new Cart.Option(option.key(), option.value()))
            .toList();

        // 엔티티는 빌더로
        Cart cart = Cart.builder()
            .count(cartRequest.cartItemCount())
            .member(member)
            .item(item)
            .optionValues(options)
            .build();

        //장바구니에 이미 존재하는 상품이면
        Cart existingCart = cartRepository.findByItemAndMember(item, member);
        if (existingCart != null) {
            throw new BusinessException(CART_IN_ITEM_DUPLICATED);
        }
        cartRepository.save(cart);

        return getCartResponse(cart);
    }

    //장바구니 수정
    @Override
    @Transactional
    public void update(Long cartId, UpdateCartRequest cartRequest, Member member) {
        Cart cart = existMemberCartCheck(cartId, member);

        cart.updateCart(cartRequest.count());
        cartRepository.save(cart);
    }

    //장바구니 전체 조회
    @Override
    @Transactional(readOnly = true)
    public CartPageResponse getAll(Pageable pageable, Member member) {
        //회원에 해당하는 전체 장바구니
        Page<Cart> cartList = cartRepository.findAllByMember(member, pageable);

        List<CartResponse> cartItems = cartList.getContent().stream()
            .map(cart -> new CartResponse(
                cart.getCartId(),
                cart.getItem().getItemId(),
                cart.getItem().getItemName(),
                cart.getItem().getItemPrice(),
                cart.getCount(),
                cart.getItemState(),
                cart.getItem().getItemOption().getOptionValues().stream()
                    .map(option -> new CartResponse.Option(option.key(), option.value()))
                    .toList()
            ))
            .toList();

        return new CartPageResponse(
            cartList.getTotalPages(),
            (int) cartList.getTotalElements(),
            cartList.getNumber(),
            cartList.getSize(),
            cartItems
        );
    }

    //체크된 장바구니들 삭제
    @Override
    @Transactional
    public void deleteSelectedCarts(List<Long> cartIds, Member member) {
        for (Long cartId : cartIds) {
            Cart cart = existMemberCartCheck(cartId, member);
            cartRepository.delete(cart);
        }
    }

    //상품 존재 여부 확인
    private Item existItemCheck(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_ITEM, "상품이 존재하지 않습니다."));
    }

    //회원의 장바구니 존재 여부 확인
    private Cart existMemberCartCheck(Long cartId, Member member) {
        return cartRepository.findByCartIdAndMember(cartId, member)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_CART));
    }

    private CartResponse getCartResponse(Cart cart) {
        return new CartResponse(
            cart.getCartId(),
            cart.getItem().getItemId(),
            cart.getItem().getItemName(),
            cart.getItem().getItemPrice(),
            cart.getCount(),
            cart.getItem().getItemState(),
            cart.getItem().getItemOption().getOptionValues().stream()
                .map(option -> new CartResponse.Option(option.key(), option.value()))
                .toList()
        );
    }
}
