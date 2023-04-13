package com.zagvladimir.mappers;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.dto.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.dto.response.ItemLeasedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemLeasedMapper {

    @Mapping(source = "renter.id", target = "renterId")
    ItemLeasedResponse toResponse(ItemLeased itemLeased);

    ItemLeased convertCreateRequest(ItemLeasedCreateRequest request);
}
