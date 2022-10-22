package com.zagvladimir.controller.requests.country;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountryCreateRequest {

    @NotBlank
    private String countryName;

    private Status status = Status.ACTIVE;
}
