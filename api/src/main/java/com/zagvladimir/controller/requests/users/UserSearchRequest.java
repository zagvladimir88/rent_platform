package com.zagvladimir.controller.requests.users;

import lombok.Data;

@Data
public class UserSearchRequest {

    private String limit;

    private String offset;

    private String searchQuery;

}
