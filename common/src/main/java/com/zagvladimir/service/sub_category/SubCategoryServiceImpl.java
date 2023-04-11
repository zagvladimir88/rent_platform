package com.zagvladimir.service.sub_category;

import com.zagvladimir.domain.SubCategory;
import com.zagvladimir.dto.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.dto.response.SubCategoryResponse;
import com.zagvladimir.mappers.SubItemTypeMapper;
import com.zagvladimir.repository.CategoryRepository;
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
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;
    private final SubItemTypeMapper subItemTypeMapper;

    @Override
    public Page<SubCategoryResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(subItemTypeMapper::toResponse);
    }

    @Override
    public List<SubCategory> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public SubCategoryResponse create(SubCategoryCreateRequest request) {
        SubCategory subCategory = subItemTypeMapper.convertCreateRequest(request);
        Long categoryId = request.getCategoryId();
        subCategory.setCategory(
                categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new));
        repository.save(subCategory);
        return repository
                .findById(subCategory.getId())
                .map(subItemTypeMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Sub category not created"));
    }

    @Override
    public SubCategoryResponse findById(Long subItemTypeId) {
        return repository
                .findById(subItemTypeId)
                .map(subItemTypeMapper::toResponse)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        String.format("Entity with id:%d not found", subItemTypeId)));
    }

    @Transactional
    @Override
    public SubCategory update(SubCategory subCategory) {
        return repository.save(subCategory);
    }

    @Transactional
    @Override
    public Long delete(Long subItemTypeId) {
        repository.deleteById(subItemTypeId);
        return subItemTypeId;
    }
}
