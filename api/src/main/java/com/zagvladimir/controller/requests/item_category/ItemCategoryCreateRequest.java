package com.zagvladimir.controller.requests.item_category;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class ItemCategoryCreateRequest {

    private String categoryName;

    private Status status;
}
