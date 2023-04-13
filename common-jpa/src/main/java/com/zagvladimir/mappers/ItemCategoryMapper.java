package com.zagvladimir.mappers;

import com.zagvladimir.domain.Category;
import com.zagvladimir.dto.requests.category.CategoryCreateRequest;
import com.zagvladimir.dto.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ItemCategoryMapper {

    CategoryResponse toResponse(Category category);

    Category convertCreateRequest(CategoryCreateRequest request);
}
