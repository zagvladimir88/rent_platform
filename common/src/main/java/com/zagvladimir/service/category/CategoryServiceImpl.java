package com.zagvladimir.service.category;

import com.zagvladimir.domain.Category;
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

  @Override
  public Page<Category> findAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Transactional
  @Override
  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category findById(Long itemCategoryId) {

    return categoryRepository
        .findById(itemCategoryId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
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
}
