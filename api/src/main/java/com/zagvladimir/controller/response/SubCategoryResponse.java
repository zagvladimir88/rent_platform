package com.zagvladimir.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponse {

    private Long id;

    private String subCategoryName;

    private Long categoryId;
}
