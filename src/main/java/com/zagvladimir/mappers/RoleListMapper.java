package com.zagvladimir.mappers;

import com.zagvladimir.domain.Role;
import com.zagvladimir.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleListMapper {
    List<RoleDTO> toDTOs(List<Role> roles);
}
