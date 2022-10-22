package com.zagvladimir.service.impl;

import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.repository.ItemCategoryRepository;
import com.zagvladimir.service.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;


    @Override
    public Page<ItemCategory> findAll(Pageable pageable) {
        return itemCategoryRepository.findAll(pageable);
    }

    @Override
    public List<ItemCategory> findAll() {
        return itemCategoryRepository.findAll();
    }

    @Override
    public ItemCategory create(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    @Override
    public Optional<ItemCategory> findById(Long itemCategoryId) {
        return itemCategoryRepository.findById(itemCategoryId);
    }

    @Override
    public ItemCategory update(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    @Override
    public Long delete(Long itemCategoryId) {
        itemCategoryRepository.deleteById(itemCategoryId);
        return itemCategoryId;
    }
}
