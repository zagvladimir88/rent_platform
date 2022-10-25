package com.zagvladimir.service.item_category;

import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public ItemCategory create(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    @Override
    public Optional<ItemCategory> findById(Long itemCategoryId) {
        return itemCategoryRepository.findById(itemCategoryId);
    }

    @Transactional
    @Override
    public ItemCategory update(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    @Transactional
    @Override
    public Long delete(Long itemCategoryId) {
        itemCategoryRepository.deleteById(itemCategoryId);
        return itemCategoryId;
    }
}
