package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.controller.response.ItemResponse;
import com.zagvladimir.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ItemMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "subItemType.id", target = "subItemTypeId")
    ItemResponse toItemResponse(Item item);

    Item itemCreateRequestToItem(ItemCreateRequest request);

}
