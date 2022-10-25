package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.item_category.ItemCategoryCreateRequest;
import com.zagvladimir.controller.response.ItemCategoryResponse;
import com.zagvladimir.domain.ItemCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ItemCategoryMapper {

    ItemCategoryResponse toResponse(ItemCategory itemCategory);

    ItemCategory convertCreateRequest(ItemCategoryCreateRequest request);
}
