package com.zagvladimir.service.sub_category;

import com.zagvladimir.domain.Category;
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

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubItemTypeMapper subItemTypeMapper;

    public Page<SubCategoryResponse> getAllSubCategories(Pageable pageable) {
        return subCategoryRepository.findAll(pageable)
                .map(subItemTypeMapper::toResponse);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Transactional
    public SubCategoryResponse createSubCategory(SubCategoryCreateRequest request) {
        SubCategory subCategory = subItemTypeMapper.convertCreateRequest(request);
        Long categoryId = request.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Category with id:%d not found", categoryId)));
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subItemTypeMapper.toResponse(savedSubCategory);
    }

    public SubCategoryResponse getSubCategoryById(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Subcategory with id:%d not found", subCategoryId)));
        return subItemTypeMapper.toResponse(subCategory);
    }

    @Transactional
    public SubCategory updateSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Transactional
    public Long deleteSubCategory(Long subCategoryId) {
        subCategoryRepository.deleteById(subCategoryId);
        return subCategoryId;
    }
}
