package com.example.review.domain.members.repository;

import com.example.review.domain.items.entity.Item;
import com.example.review.domain.members.entity.Cart;
import com.example.review.domain.members.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByItemAndMember(Item item, Member member);

    Optional<Cart> findByCartIdAndMember(Long cartId, Member member);

    @Query("select c from Cart c join fetch c.item where c.member = :member")
    Page<Cart> findAllByMember(Member member, Pageable pageable);

    List<Cart> findAllByMember(Member member);

    List<Cart> findAllByItem(Item item);
}
