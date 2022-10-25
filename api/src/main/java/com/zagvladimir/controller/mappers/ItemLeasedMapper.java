package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.controller.response.ItemLeasedResponse;
import com.zagvladimir.domain.ItemLeased;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemLeasedMapper {

    @Mapping(source = "renter.id", target = "renterId")
    ItemLeasedResponse toResponse(ItemLeased itemLeased);

    ItemLeased convertCreateRequest(ItemLeasedCreateRequest request);
}
