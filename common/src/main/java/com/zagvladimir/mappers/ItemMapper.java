package com.zagvladimir.mappers;

import com.zagvladimir.domain.Item;
import com.zagvladimir.dto.requests.items.ItemCreateRequest;
import com.zagvladimir.dto.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ItemMapper {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    ItemResponse toResponse(Item item);

    Item convertCreateRequest(ItemCreateRequest request);

}
