package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.controller.response.RoleResponse;
import com.zagvladimir.domain.Role;
import org.mapstruct.Mapper;


@Mapper
public interface RoleMapper {

    RoleResponse toResponse(Role role);

    Role convertCreateRequest(RoleCreateRequest request);
}
