package com.zagvladimir.service;

import com.zagvladimir.domain.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item create(Item object);

    Item findById(Long itemId);

    Item update(Item object);

    Long delete(Long itemId);

    List<Item> findItemsByCategoryId(Long itemTypeId);

    List<Item> findItemsByItemName(String name);

    List<Item> search(int limit, int offset);
}
