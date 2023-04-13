package com.zagvladimir.service.item;

import com.zagvladimir.domain.Item;
import com.zagvladimir.dto.requests.items.ItemCreateRequest;
import com.zagvladimir.dto.response.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Page<ItemResponse> findAll(Pageable pageable);

    ItemResponse create(ItemCreateRequest request);

    ItemResponse findById(Long itemId);

    Item update(Item object);

    Long delete(Long itemId);

}
