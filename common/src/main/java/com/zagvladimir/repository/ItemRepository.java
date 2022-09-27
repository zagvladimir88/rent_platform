package com.zagvladimir.repository;

import com.zagvladimir.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>{

    List<Item> findItemsByItemTypeId(Long typeId);
    List<Item> findItemsByItemName(String itemName);
}
