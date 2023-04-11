package com.zagvladimir.dto.requests.category;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryCreateRequest {

    @NotNull
    @Size(min = 2, max = 35)
    @Schema(defaultValue = "Test Category", type = "string" , description = "Category name")
    private String categoryName;


    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status;
}
