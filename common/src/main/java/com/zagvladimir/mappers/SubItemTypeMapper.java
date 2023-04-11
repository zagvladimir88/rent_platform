package com.zagvladimir.mappers;


import com.zagvladimir.domain.SubCategory;
import com.zagvladimir.dto.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.dto.response.SubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubItemTypeMapper {

    @Mapping(source = "category.id", target = "categoryId")
    SubCategoryResponse toResponse(SubCategory subCategory);

    SubCategory convertCreateRequest(SubCategoryCreateRequest request);

}
