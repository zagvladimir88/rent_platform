package com.zagvladimir.controller.requests.role;

import com.zagvladimir.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCreateRequest {

    private String name;

    private Status status = Status.ACTIVE;

}
