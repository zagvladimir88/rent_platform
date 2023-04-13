package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.dto.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.dto.response.ItemLeasedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ItemLeasedService {

    Page<ItemLeasedResponse> findAll(Pageable page);

    ItemLeasedResponse create(ItemLeasedCreateRequest request);

    ItemLeasedResponse findById(Long itemLeasedId);

    ItemLeased update(ItemLeased object);

    Long delete(Long itemLeasedId);

    String getRenterName(Long id);

    List<ItemLeasedResponse> findAllByRenterId(Long useId);

}
