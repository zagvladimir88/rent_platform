package com.zagvladimir.service.item_category;

import com.zagvladimir.domain.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItemCategoryService {

    Page<ItemCategory> findAll(Pageable pageable);

    List<ItemCategory> findAll();

    ItemCategory create(ItemCategory itemCategory);

    Optional<ItemCategory> findById(Long itemCategoryId);

    ItemCategory update(ItemCategory itemCategory);

    Long delete(Long itemCategoryId);

}
