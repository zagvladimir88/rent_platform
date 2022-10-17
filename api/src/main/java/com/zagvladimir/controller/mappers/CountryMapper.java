package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.country.CountryCreateRequest;
import com.zagvladimir.controller.requests.country.CountryUpdateRequest;
import com.zagvladimir.controller.response.CountryResponse;
import com.zagvladimir.domain.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CountryMapper {

    CountryResponse toResponse(Country country);

    Country countryFromCreateRequest(CountryCreateRequest request);
    Country updateFromRequest(CountryUpdateRequest request, @MappingTarget Country country);
}
