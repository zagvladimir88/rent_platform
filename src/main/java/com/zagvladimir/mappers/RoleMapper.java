package com.zagvladimir.mappers;

import com.zagvladimir.domain.Role;
import com.zagvladimir.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDTO toDTO(Role role);
}
