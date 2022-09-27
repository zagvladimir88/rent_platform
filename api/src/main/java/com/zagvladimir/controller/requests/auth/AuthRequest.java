package com.zagvladimir.controller.requests.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String user_login;

    private String user_password;
}
