package com.zagvladimir.dto.requests.sub_category;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SubCategoryCreateRequest {

    @Size(min = 2, max = 100)
    @Schema(defaultValue = "testCategory", type = "string" , description = "Sub Category Name")
    private String subCategoryName;

    @Schema(defaultValue = "2", type = "Long" , description = "Category id")
    private long categoryId;


    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status;
}
