package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;


public interface ItemLeasedService {

    Page<ItemLeased> findAll(Pageable page);

    ItemLeased create(ItemLeased itemLeased, Long renterId);

    ItemLeased findById(Long itemLeasedId);

    ItemLeased update(ItemLeased object);

    Long delete(Long itemLeasedId);

    boolean confirmItemBooking(Long itemLeasedId) throws MessagingException;
}
