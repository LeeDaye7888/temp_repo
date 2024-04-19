package com.example.review.domain.members.entity;

import com.example.review.domain.BaseEntity;
import com.example.review.domain.items.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "star", nullable = false)
    private int star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Review(String reviewTitle, String reviewContent, int star, Item item, Member member) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.star = star;
        this.item = item;
        this.member = member;
    }

    public void updateReview(String reviewTitle, String reviewContent, int star) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.star = star;
    }

    public void changeMemberToNull() {
        this.member = null;
    }

    public void changeItemToNull() {
        this.item = null;
    }

}

