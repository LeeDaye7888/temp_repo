package com.example.review.domain.items.repository;

import com.example.review.domain.items.entity.Category;
import com.example.review.domain.items.entity.Item;
import com.example.review.domain.members.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemName(String itemName);
    Page<Item> findByMember(Pageable pageable, Member member);
    Page<Item> findByCategory(Pageable pageable, Category category);
    List<Item> findAllByMember(Member member);
    Optional<Item> findFirstByCategory(Category category);
}
