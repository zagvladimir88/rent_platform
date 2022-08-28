package com.zagvladimir.repository.item;

import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.CRUDRepository;

import java.util.List;
import java.util.Map;

public interface ItemRepositoryInterface extends CRUDRepository<Long, Item> {
    List<Item> getItemsByCategory(int itemTypeId);

    List<Item> searchItemsByName(String name);


}
