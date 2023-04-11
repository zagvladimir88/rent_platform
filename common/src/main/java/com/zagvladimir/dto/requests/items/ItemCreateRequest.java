package com.zagvladimir.dto.requests.items;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
public class ItemCreateRequest {

    @Schema(defaultValue = "Makita hr2470ft", type = "string" , description = "Item name")
    @NotBlank
    @Size(min = 2, max = 255)
    private String itemName;

    @NotBlank
    @Size(min = 2, max =50)
    @Schema(defaultValue = "Makita", type = "string" , description = "Brand name")
    private String brand;

    @NotNull
    @Schema(defaultValue = "5", type = "Long" , description = "Item type id")
    private long subCategoryId;

    @Schema(defaultValue = "Rotary Hammer makita hr2470ft", type = "string" , description = "Item description")
    @NotEmpty
    private String description;

    @Schema(defaultValue = "5.0", type = "number($double)" , description = "Price per hour")
    @Positive
    private int pricePerDay;

    @NotNull
    @Schema(defaultValue = "true", type = "boolean" , description = "Status of available")
    private Boolean available;

    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status = Status.ACTIVE;

}
