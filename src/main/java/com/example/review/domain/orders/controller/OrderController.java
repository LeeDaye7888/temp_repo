package com.example.review.domain.orders.controller;

import com.example.review.domain.orders.dto.*;
import com.example.review.domain.orders.service.implement.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "주문 관련 api", description = "주문 등록, 취소, 조회 api입니다.")
public class OrderController {

    private final OrderServiceImpl orderService;

    //주문번호 UUID 생성
    @PostMapping("/order-keys")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "주문번호(UUID) 생성 api", description = "주문 관련 주문번호(UUID)를 생성하는 api입니다.")
    public OrderKeyResponse generateOrderKey() {
        UUID orderKey = UUID.randomUUID();
        return new OrderKeyResponse(orderKey);
    }

    //주문 생성
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "주문 생성 api", description = "주문을 생성하는 api 입니다.")
    public OrderResponse addOrder(@Valid @RequestBody OrderRequest orderRequest,
        @AuthenticationPrincipal User user) {
        return orderService.create(orderRequest, user);
    }

    //주문(결제) 취소
    @DeleteMapping("/orders")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "결제 취소 api", description = "결제를 취소하는 api 입니다.")
    public void deleteOrder(@RequestBody PayCancelRequest payCancelRequest,
        @AuthenticationPrincipal User user) {
        orderService.payCancel(payCancelRequest, user);
    }

    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "주문 상세 조회 api", description = "주문을 상세 조회하는 api 입니다.")
    public OrderResponse getOneOrders(@AuthenticationPrincipal User user,
        @PathVariable Long orderId) {
        return orderService.getOne(user, orderId);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "주문 목록 전체 조회 api", description = "자신의 주문 목록을 전체 조회하는 api 입니다.")
    public OrderPageResponse getAllOrders(@AuthenticationPrincipal User user,
        Pageable pageable) {
        return orderService.getAll(user, pageable);
    }
}
