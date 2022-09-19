package com.zagvladimir.mappers;

import com.zagvladimir.domain.User;
import com.zagvladimir.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserListMapper {
    List<UserDTO> toDTOs(List<User> user);
}
