package com.zagvladimir.repository;

import com.zagvladimir.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemRepository extends
        CrudRepository<Item,Long>,
        JpaRepository<Item,Long>,
        PagingAndSortingRepository<Item,Long> {

    List<Item> findItemsByItemTypeId(Long typeId);
    List<Item> findItemsByItemName(String itemName);
}
