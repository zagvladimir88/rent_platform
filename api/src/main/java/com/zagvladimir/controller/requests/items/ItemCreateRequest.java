package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ItemCreateRequest {

    @NotBlank
    private String itemName;

    @NotNull
    private long itemTypeId;

    private Long locationId;

    private String itemLocation;

    private String description;

    private long ownerId;

    @Positive
    private int pricePerHour;

    private Boolean available;

    private Status status = Status.ACTIVE;

}
