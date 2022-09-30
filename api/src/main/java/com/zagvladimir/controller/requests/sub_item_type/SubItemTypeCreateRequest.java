package com.zagvladimir.controller.requests.sub_item_type;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class SubItemTypeCreateRequest {

    private String subCategoryName;
    private long categoryId;
    private Status status;
}
