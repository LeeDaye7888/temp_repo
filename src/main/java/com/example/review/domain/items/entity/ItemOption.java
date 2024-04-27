package com.example.review.domain.items.entity;

import com.example.review.domain.BaseEntity;
import java.util.List;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "item_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "option_values", columnDefinition = "longtext")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Option> optionValues;

    @OneToOne(mappedBy = "itemOption", fetch = FetchType.LAZY)
    private Item item;

    @Builder
    public ItemOption(List<Option> optionValues) {
        this.optionValues = optionValues;
    }

    public void updateOption(List<Option> optionValues) {
        this.optionValues = optionValues;
    }

    public record Option(
        String key,
        String value
    ) {

    }
}
