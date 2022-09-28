package com.zagvladimir.controller.requests.country;

import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class CountryUpdateRequest {
    private String countryName;

    private Status status;
}
