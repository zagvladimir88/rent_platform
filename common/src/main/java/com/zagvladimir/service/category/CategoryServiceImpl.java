package com.zagvladimir.service.category;

import com.zagvladimir.domain.Category;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.dto.requests.category.CategoryCreateRequest;
import com.zagvladimir.dto.response.CategoryResponse;
import com.zagvladimir.mappers.ItemCategoryMapper;
import com.zagvladimir.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemCategoryMapper itemCategoryMapper;

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(itemCategoryMapper::toResponse);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public CategoryResponse create(CategoryCreateRequest request) {
        Category newCategory = itemCategoryMapper.convertCreateRequest(request);
        return itemCategoryMapper.toResponse(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryResponse findById(Long itemCategoryId) {
        return categoryRepository
                .findById(itemCategoryId)
                .map(itemCategoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("The category with id:%d not found", itemCategoryId)));
    }

    @Transactional
    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Long delete(Long itemCategoryId) {
        categoryRepository.deleteById(itemCategoryId);
        return itemCategoryId;
    }

    @Override
    public Long softDelete(Long categoryId) {
        Category category = getCategoryById(categoryId);
        category.setStatus(Status.DELETED);
        categoryRepository.save(category);
        return categoryId;
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("The category with id: %d not found", categoryId)));
    }
}
