package com.zagvladimir.service.item;

import com.zagvladimir.domain.Item;
import com.zagvladimir.dto.requests.items.ItemCreateRequest;
import com.zagvladimir.dto.response.ItemResponse;
import com.zagvladimir.mappers.ItemMapper;
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
    private final ItemMapper itemMapper;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Page<ItemResponse> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable).map(itemMapper::toResponse);
    }

    @Transactional
    @Override
    public ItemResponse create(ItemCreateRequest request) {
        Item item = itemMapper.convertCreateRequest(request);
        Long subCategoryId = request.getSubCategoryId();
        item.setSubCategory(subCategoryRepository.findById(subCategoryId).orElseThrow(EntityNotFoundException::new));
        return itemMapper.toResponse(itemRepository.save(item));
    }

    @Override
    public ItemResponse findById(Long itemId) {
        return itemMapper.toResponse(itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new)
        );
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
