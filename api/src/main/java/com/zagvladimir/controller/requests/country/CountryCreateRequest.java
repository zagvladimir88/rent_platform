package com.zagvladimir.controller.requests.country;

import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class CountryCreateRequest {

    private String countryName;

    private Status status = Status.ACTIVE;
}
