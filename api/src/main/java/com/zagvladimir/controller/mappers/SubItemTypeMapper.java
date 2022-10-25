package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.sub_item_type.SubItemTypeCreateRequest;
import com.zagvladimir.controller.response.SubItemTypeResponse;
import com.zagvladimir.domain.SubItemType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubItemTypeMapper {

    @Mapping(source = "itemCategory.id", target = "itemCategoryId")
    SubItemTypeResponse toResponse(SubItemType subItemType);

    SubItemType convertCreateRequest(SubItemTypeCreateRequest request);

}
