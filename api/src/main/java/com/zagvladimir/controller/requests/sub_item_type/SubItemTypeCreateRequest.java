package com.zagvladimir.controller.requests.sub_item_type;

import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class SubItemTypeCreateRequest {

    private String subCategoryName;
    private ItemCategory itemCategory;
    private Status status;
}
