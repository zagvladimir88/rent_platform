package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.location.LocationCreateRequest;
import com.zagvladimir.controller.requests.location.LocationUpdateRequest;
import com.zagvladimir.controller.response.LocationResponse;
import com.zagvladimir.domain.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper
public interface LocationMapper {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.countryName", target = "countryName")
    LocationResponse toResponse(Location location);

    Location convertCreateRequest(LocationCreateRequest request);

    Location convertUpdateRequest(LocationUpdateRequest request, @MappingTarget Location location);

}
