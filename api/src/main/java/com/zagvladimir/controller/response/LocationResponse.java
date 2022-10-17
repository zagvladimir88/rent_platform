package com.zagvladimir.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {

    private Long id;

    private String postalCode;

    private String name;

    private String description;

    private String countryName;

    private Long countryId;

}
