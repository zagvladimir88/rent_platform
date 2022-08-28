package com.zagvladimir.service;

import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.item.ItemRepositoryInterface;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepositoryInterface itemRepository;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item create(Item object) {
        return itemRepository.create(object);
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public Item update(Item object) {
        return itemRepository.update(object);
    }

    @Override
    public Long delete(Long itemId) {
        return itemRepository.delete(itemId);
    }

    @Override
    public List<Item> getItemsByCategory(int itemTypeId) {
        return itemRepository.getItemsByCategory(itemTypeId);
    }

    @Override
    public List<Item> searchItemsByName(String name) {
        return itemRepository.searchItemsByName(name);
    }
}
