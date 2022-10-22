package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class ItemCreateRequest {

    @Schema(defaultValue = "Makita hr2470ft", type = "string" , description = "Item name")
    @NotBlank
    private String itemName;

    @Schema(defaultValue = "5", type = "Long" , description = "Item type id")
    private long itemTypeId;

    @Schema(defaultValue = "2", type = "Long" , description = "Location Id")
    private Long locationId;

    @Schema(defaultValue = "Microdistrict 17", type = "string" , description = "Item address")
    @NotEmpty
    private String itemLocation;

    @Schema(defaultValue = "Rotary Hammer makita hr2470ft", type = "string" , description = "Item description")
    @NotEmpty
    private String description;

    @Schema(defaultValue = "3", type = "string" , description = "The id of the user who owns")
    private long ownerId;

    @Schema(defaultValue = "5.0", type = "number($double)" , description = "Price per hour")
    @Positive
    private int pricePerHour;

    @Schema(defaultValue = "true", type = "boolean" , description = "Status of available")
    private Boolean available;

    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status = Status.ACTIVE;

}
