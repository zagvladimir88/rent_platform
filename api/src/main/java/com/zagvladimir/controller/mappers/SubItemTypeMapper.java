package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.controller.response.SubCategoryResponse;
import com.zagvladimir.domain.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubItemTypeMapper {

    @Mapping(source = "category.id", target = "categoryId")
    SubCategoryResponse toResponse(SubCategory subCategory);

    SubCategory convertCreateRequest(SubCategoryCreateRequest request);

}
