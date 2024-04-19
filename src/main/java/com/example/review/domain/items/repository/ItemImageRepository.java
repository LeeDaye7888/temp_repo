package com.example.review.domain.items.repository;

import com.example.review.domain.items.entity.Item;
import com.example.review.domain.items.entity.ItemImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItem(Item item);
}
