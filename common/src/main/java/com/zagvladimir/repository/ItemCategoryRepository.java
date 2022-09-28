package com.zagvladimir.repository;

import com.zagvladimir.domain.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory,Long> {
}
