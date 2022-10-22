package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class ItemCreateRequest {

    @NotBlank
    private String itemName;

    private long itemTypeId;

    private Long locationId;

    @NotEmpty
    private String itemLocation;

    @NotEmpty
    private String description;

    private long ownerId;

    @Positive
    private int pricePerHour;

    private Boolean available;

    private Status status = Status.ACTIVE;

}
