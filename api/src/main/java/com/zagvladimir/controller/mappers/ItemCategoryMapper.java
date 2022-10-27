package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.category.CategoryCreateRequest;
import com.zagvladimir.controller.response.CategoryResponse;
import com.zagvladimir.domain.Category;
import org.mapstruct.Mapper;

@Mapper
public interface ItemCategoryMapper {

    CategoryResponse toResponse(Category category);

    Category convertCreateRequest(CategoryCreateRequest request);
}
