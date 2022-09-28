package com.zagvladimir.controller.requests;

import lombok.Data;

@Data
public class SearchRequest {

    private String limit;

    private String offset;

    private String searchQuery;
}
