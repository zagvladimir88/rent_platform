package com.zagvladimir.service.sub_category;

import com.zagvladimir.domain.SubCategory;
import com.zagvladimir.repository.CategoryRepository;
import com.zagvladimir.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

  private final SubCategoryRepository repository;
  private final CategoryRepository categoryRepository;

  @Override
  public Page<SubCategory> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public List<SubCategory> findAll() {
    return repository.findAll();
  }

  @Transactional
  @Override
  public SubCategory create(SubCategory subCategory, Long itemCategoryId) {
    subCategory.setCategory(
        categoryRepository.findById(itemCategoryId).orElseThrow(EntityNotFoundException::new));
    return repository.save(subCategory);
  }

  @Override
  public Optional<SubCategory> findById(Long subItemTypeId) {
    return repository.findById(subItemTypeId);
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
