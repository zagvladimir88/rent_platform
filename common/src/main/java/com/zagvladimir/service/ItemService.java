package com.zagvladimir.service;

import com.zagvladimir.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Page<Item> findAll(Pageable pageable);

    Item create(Item object);

    Item findById(Long itemId);

    Item update(Item object);

    Long delete(Long itemId);

    List<Item> findItemsByCategoryId(Long itemTypeId);

    List<Item> findItemsByItemName(String name);

    List<Item> search(int limit, int offset);
}
