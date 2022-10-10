package com.zagvladimir.controller.requests;

import lombok.Data;

@Data
public class SearchRequest {

    private String page;

    private String size;

    private String sort;
}
