package com.zagvladimir.service.sub_category;

import com.zagvladimir.domain.SubCategory;
import com.zagvladimir.dto.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.dto.response.SubCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubCategoryService {

    Page<SubCategoryResponse> getAllSubCategories(Pageable pageable);

    List<SubCategory> getAllSubCategories();

    SubCategoryResponse createSubCategory(SubCategoryCreateRequest request);

    SubCategoryResponse getSubCategoryById(Long subItemTypeId);

    SubCategory updateSubCategory(SubCategory subCategory);

    Long deleteSubCategory(Long subItemTypeId);
}
