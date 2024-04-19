package com.example.review.domain.members.entity;


import com.example.review.domain.BaseEntity;
import com.example.review.domain.items.entity.Item;
import com.example.review.domain.items.entity.ItemState;

import java.util.List;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "count", nullable = false)
    private int count; //장바구니에 담긴 상품 수량 예)노트북 2개 담음

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "sold_out_state")
    private ItemState itemState;

    @Column(name = "option_values", columnDefinition = "longtext", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Option> optionValues;

    @Builder
    public Cart(int count, Item item, Member member, ItemState itemState,
        List<Option> optionValues) {
        this.count = count;
        this.item = item;
        this.member = member;
        this.itemState = itemState;
        this.optionValues = optionValues;
    }

    public void updateCart(int count) {
        this.count = count;
    }

    public record Option(
        String key,
        String value
    ) {

    }

}
