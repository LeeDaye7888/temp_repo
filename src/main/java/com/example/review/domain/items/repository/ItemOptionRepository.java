package com.example.review.domain.items.repository;

import com.example.review.domain.items.entity.Item;
import com.example.review.domain.items.entity.ItemOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
    List<ItemOption> findByItem(Item item);
}
