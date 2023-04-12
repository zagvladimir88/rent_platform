package com.zagvladimir.service.category;

import com.zagvladimir.domain.Category;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.dto.requests.category.CategoryCreateRequest;
import com.zagvladimir.dto.response.CategoryResponse;
import com.zagvladimir.mappers.ItemCategoryMapper;
import com.zagvladimir.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ItemCategoryMapper itemCategoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAll_ShouldReturnPageOfCategoryResponses() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Category itemCategory = new Category();
        Page<Category> page = new PageImpl<>(Collections.singletonList(itemCategory));

        CategoryResponse categoryResponse = new CategoryResponse();

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(itemCategoryMapper.toResponse(ArgumentMatchers.any())).thenReturn(categoryResponse);

        Page<CategoryResponse> result = categoryService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void create_ShouldReturnCreatedCategoryResponse()  {
        CategoryCreateRequest request = new CategoryCreateRequest();

        Category category = new Category();
        category.setCategoryName("test");
        category.setStatus(Status.ACTIVE);

        CategoryResponse expectedCategory = new CategoryResponse();
        expectedCategory.setCategoryName("test");
        expectedCategory.setId(1L);

        when(itemCategoryMapper.convertCreateRequest(request)).thenReturn(category);
        when(itemCategoryMapper.toResponse(category)).thenReturn(expectedCategory);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponse resultCategoryResponse = categoryService.create(request);

        assertEquals(expectedCategory, resultCategoryResponse);
        verify(itemCategoryMapper,times(1)).convertCreateRequest(request);
        verify(itemCategoryMapper,times(1)).toResponse(category);
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    void findById_WithExistingCategory_ReturnsCategoryResponse() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");

        CategoryResponse expectedCategoryResponse = new CategoryResponse();
        expectedCategoryResponse.setCategoryName("Test Category");
        expectedCategoryResponse.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(itemCategoryMapper.toResponse(category)).thenReturn(expectedCategoryResponse);

        CategoryResponse categoryResponse = categoryService.findById(1L);

        assertNotNull(categoryResponse);
        assertEquals(expectedCategoryResponse, categoryResponse);
    }

    @Test
    void findById_WithNonExistingCategory_ThrowsEntityNotFoundException() {
        when(categoryRepository.findById(2L)).thenThrow( new EntityNotFoundException("The category with id:2 not found"));

        assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(2L);
        }, "The category with id:3 not found");
    }

    @Test
    void findById_WithNullCategory_ThrowsIllegalArgumentException() {
        Mockito.when(categoryRepository.findById(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.findById(null);
        });
    }

    @Test
    void update_ShouldReturnUpdatedCategory() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setCategoryName("Test Category");

        when(categoryRepository.save(expectedCategory)).thenReturn(expectedCategory);

        Category result = categoryService.update(expectedCategory);

        assertEquals(expectedCategory, result);
        verify(categoryRepository, times(1)).save(expectedCategory);
    }

    @Test
    void delete_ShouldReturnDeletedCategoryId() {
        Long expectedCategoryId = 1L;

        doNothing().when(categoryRepository).deleteById(expectedCategoryId);

        Long resultId = categoryService.delete(expectedCategoryId);

        assertEquals(expectedCategoryId, resultId);

        verify(categoryRepository, times(1)).deleteById(expectedCategoryId);
    }

    @Test
    void testSoftDeleteWithNonExistingCategory() {
        Long expectedCategoryId = 1L;
        when(categoryRepository.findById(expectedCategoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            categoryService.softDelete(expectedCategoryId);
        });
    }

    @Test
    void softDelete_ShouldChangeCategoryStatusToDeleted() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setCategoryName("Test Category");

        Long expectedCategoryId = 1L;

        when(categoryRepository.findById(expectedCategoryId)).thenReturn(Optional.of(expectedCategory));
        when(categoryRepository.save(expectedCategory)).thenReturn(expectedCategory);

        Long deletedCategoryId = categoryService.softDelete(expectedCategoryId);

        assertEquals(expectedCategoryId, deletedCategoryId);
        assertEquals(Status.DELETED, expectedCategory.getStatus());

        verify(categoryRepository, times(1)).findById(expectedCategoryId);
        verify(categoryRepository, times(1)).save(expectedCategory);
    }
}