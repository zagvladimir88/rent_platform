package com.zagvladimir.service;

import com.zagvladimir.domain.ItemLeased;

import java.util.List;

public interface ItemLeasedService {

    List<ItemLeased> findAll();

    ItemLeased create(ItemLeased object);

    ItemLeased findById(Long itemLeasedId);

    ItemLeased update(ItemLeased object);

    Long delete(Long itemLeasedId);
}
