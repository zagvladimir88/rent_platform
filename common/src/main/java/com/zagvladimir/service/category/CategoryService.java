package com.zagvladimir.service.category;

import com.zagvladimir.domain.Category;
import com.zagvladimir.dto.requests.category.CategoryCreateRequest;
import com.zagvladimir.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

  Page<CategoryResponse> findAll(Pageable pageable);

  List<Category> findAll();

  CategoryResponse create(CategoryCreateRequest request);

  CategoryResponse findById(Long itemCategoryId);

  Category update(Category category);

  Long delete(Long itemCategoryId);

  Long softDelete(Long categoryId);
}
