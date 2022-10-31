package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ItemLeasedService {

    Page<ItemLeased> findAll(Pageable page);

    ItemLeased create(ItemLeased itemLeased, Long renterId);

    ItemLeased findById(Long itemLeasedId);

    ItemLeased update(ItemLeased object);

    Long delete(Long itemLeasedId);

    String getRenterName(Long id);

    List<ItemLeased> findAllByRenterId(Long useId);

}
