package com.zagvladimir.service.impl;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

    private final ItemLeasedRepository itemLeasedRepository;
    private final UserService userService;

    @Transactional
    @Override
    public List<ItemLeased> findAll() {
        return itemLeasedRepository.findAll();
    }

    @Transactional
    @Override
    public ItemLeased create(ItemLeased itemLeased, Long renterId) {
        itemLeased.setRenter(userService.findById(renterId));
        itemLeased.setCreationDate(new Timestamp(new Date().getTime()));
        itemLeased.setModificationDate(itemLeased.getCreationDate());
        return itemLeasedRepository.save(itemLeased);
    }

    @Transactional
    @Override
    public ItemLeased findById(Long itemLeasedId) {
        return itemLeasedRepository.findById(itemLeasedId).get();
    }

    @Transactional
    @Override
    public ItemLeased update(ItemLeased object) {
        return itemLeasedRepository.save(object);
    }

    @Transactional
    @Override
    public Long delete(Long itemLeasedId) {
        itemLeasedRepository.deleteById(itemLeasedId);
        return itemLeasedId;
    }
}
