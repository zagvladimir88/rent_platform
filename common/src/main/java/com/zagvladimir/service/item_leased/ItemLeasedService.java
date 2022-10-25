package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;

import java.util.List;

public interface ItemLeasedService {

    List<ItemLeased> findAll();

    ItemLeased create(ItemLeased itemLeased, Long renterId);

    ItemLeased findById(Long itemLeasedId);

    ItemLeased update(ItemLeased object);

    Long delete(Long itemLeasedId);
}
