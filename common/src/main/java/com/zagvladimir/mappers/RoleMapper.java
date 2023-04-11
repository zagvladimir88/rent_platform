package com.zagvladimir.mappers;

import com.zagvladimir.domain.Role;
import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
import org.mapstruct.Mapper;


@Mapper
public interface RoleMapper {

    RoleResponse toResponse(Role role);

    Role convertCreateRequest(RoleCreateRequest request);
}
