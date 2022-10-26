package com.zagvladimir.service.sub_category;

import com.zagvladimir.domain.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubCategoryService {

    Page<SubCategory> findAll(Pageable pageable);

    List<SubCategory> findAll();

    SubCategory create(SubCategory subCategory, Long itemCategoryId);

    Optional<SubCategory> findById(Long subItemTypeId);

    SubCategory update(SubCategory subCategory);

    Long delete(Long subItemTypeId);

}
