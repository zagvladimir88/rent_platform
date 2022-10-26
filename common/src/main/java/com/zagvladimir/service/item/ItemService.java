package com.zagvladimir.service.item;

import com.zagvladimir.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Page<Item> findAll(Pageable pageable);

    Item create(Item item, Long subCategoryId);

    Item findById(Long itemId);

    Item update(Item object);

    Long delete(Long itemId);

}
