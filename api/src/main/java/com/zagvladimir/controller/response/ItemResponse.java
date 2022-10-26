package com.zagvladimir.controller.response;

import com.zagvladimir.domain.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ItemResponse {

    private Long id;

    private String itemName;

    private String brand;

    private Long subCategoryId;

    private String description;

    private double pricePerHour;

    private Boolean available;

    private Set<Image> images;

}
