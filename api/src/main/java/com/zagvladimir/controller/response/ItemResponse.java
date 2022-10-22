package com.zagvladimir.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse {

    private Long id;

    private String itemName;

    private Long subItemTypeId;

    private Long locationId;

    private String itemLocation;

    private String description;

    private Long ownerId;

    private double pricePerHour;

    private Boolean available;

//    private Set<Image> images;

}
