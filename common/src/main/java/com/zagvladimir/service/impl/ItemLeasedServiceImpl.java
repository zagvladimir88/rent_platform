package com.zagvladimir.service.impl;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.ItemLeasedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

    private final ItemLeasedRepository itemLeasedRepository;

    @Transactional
    @Override
    public List<ItemLeased> findAll() {
        return itemLeasedRepository.findAll();
    }

    @Transactional
    @Override
    public ItemLeased create(ItemLeased object) {
        return itemLeasedRepository.save(object);
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
