package com.zagvladimir.service.impl;

import com.zagvladimir.domain.SubItemType;
import com.zagvladimir.repository.ItemCategoryRepository;
import com.zagvladimir.repository.SubItemTypeRepository;
import com.zagvladimir.service.SubItemTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubItemTypeServiceImpl implements SubItemTypeService {

    private final SubItemTypeRepository repository;
    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    public Page<SubItemType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<SubItemType> findAll() {
        return repository.findAll();
    }

    @Override
    public SubItemType create(SubItemType subItemType, Long itemCategoryId) {
        subItemType.setItemCategory(itemCategoryRepository.findById(itemCategoryId).orElseThrow(EntityNotFoundException::new));
                return repository.save(subItemType);
    }

    @Override
    public Optional<SubItemType> findById(Long subItemTypeId) {
        return repository.findById(subItemTypeId);
    }

    @Override
    public SubItemType update(SubItemType subItemType) {
        return repository.save(subItemType);
    }

    @Override
    public Long delete(Long subItemTypeId) {
        repository.deleteById(subItemTypeId);
        return subItemTypeId;
    }
}
