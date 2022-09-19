package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.item.ItemRepositoryInterface;
import com.zagvladimir.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepositoryInterface itemRepository;

    @Transactional
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    @Override
    public Item create(Item object) {
        return itemRepository.create(object);
    }

    @Transactional
    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    @Override
    public Item update(Item object) {
        return itemRepository.update(object);
    }

    @Transactional
    @Override
    public Long delete(Long itemId) {
        return itemRepository.delete(itemId);
    }

    @Transactional
    @Override
    public List<Item> getItemsByCategory(int itemTypeId) {
        return itemRepository.getItemsByCategory(itemTypeId);
    }

    @Transactional
    @Override
    public List<Item> searchItemsByName(String name) {
        return itemRepository.searchItemsByName(name);
    }

    @Transactional
    @Override
    public List<Item> search(int limit, int offset) {
        return itemRepository.findAll(limit, offset);
    }
}
