package com.zagvladimir.dto.requests.role;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class RoleCreateRequest {

    @Size(min = 2, max = 35)
    @Schema(defaultValue = "ROLE_TEST", type = "string" , description = "Role name")
    private String name;


    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status = Status.ACTIVE;

}
