package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.ItemRepository;
import com.zagvladimir.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Item create(Item object) {
        return itemRepository.save(object);
    }

    @Transactional
    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Transactional
    @Override
    public Item update(Item object) {
        return itemRepository.save(object);
    }

    @Transactional
    @Override
    public Long delete(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }

    @Transactional
    @Override
    public List<Item> findItemsByCategoryId(Long itemTypeId) {
        return itemRepository.findItemBySubItemTypeId(itemTypeId);
    }

    @Transactional
    @Override
    public List<Item> findItemsByItemName(String name) {
        return itemRepository.findItemsByItemName(name);
    }

    @Transactional
    @Override
    public List<Item> search(int limit, int offset) {
        return itemRepository.findAll();
    }
}
