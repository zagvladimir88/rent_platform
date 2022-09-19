package com.zagvladimir.mappers;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.dto.RoleDTO;
import com.zagvladimir.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserRoleListMapper {
    List<RoleDTO> toDTOs(List<Role> role);
}
