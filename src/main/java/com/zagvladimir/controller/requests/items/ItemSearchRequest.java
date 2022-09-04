package com.zagvladimir.controller.requests.items;

import lombok.Data;

@Data
public class ItemSearchRequest {
    private String limit;

    private String offset;

    private String searchQuery;

}
