package com.zagvladimir.controller.requests.category;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryCreateRequest {

    @Schema(defaultValue = "Test Category", type = "string" , description = "Category name")
    private String categoryName;

    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status;
}
