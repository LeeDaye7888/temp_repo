package com.example.review.domain.items.entity;

import com.example.review.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemImage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_image_id")
    private Long itemImageId;

    @Column(name = "item_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder
    public ItemImage(String imageUrl, Item item) {
        this.imageUrl = imageUrl;
        this.item = item;
    }

    // ItemImage가 특정 Item 엔티티와의 연결을 업데이트하기 위한 메소드
    // ItemImage가 어떤 item의 이미지를 참조하는지 알도록 함
    public void updateItem(Item item) {
        this.item = item;
    }
}