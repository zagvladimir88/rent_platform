package com.zagvladimir.service.category;

import com.zagvladimir.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    List<Category> findAll();

    Category create(Category category);

    Optional<Category> findById(Long itemCategoryId);

    Category update(Category category);

    Long delete(Long itemCategoryId);

}
