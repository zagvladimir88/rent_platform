package com.zagvladimir.service.item;

import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.ItemRepository;
import com.zagvladimir.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SubCategoryRepository subCategoryRepository;

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
    public Item create(Item item, Long subCategoryId) {
        item.setSubCategory(subCategoryRepository.findById(subCategoryId).orElseThrow(EntityNotFoundException::new));
        return itemRepository.save(item);
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
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

}
