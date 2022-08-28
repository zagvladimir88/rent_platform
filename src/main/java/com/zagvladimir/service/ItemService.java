package com.zagvladimir.service;

import com.zagvladimir.domain.Item;
import com.zagvladimir.domain.User;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> findAll();

    Item create(Item object);

    Item findById(Long itemId);

    Item update(Item object);

    Long delete(Long itemId);

    List<Item> getItemsByCategory(int itemTypeId);

    List<Item> searchItemsByName(String name);
}
