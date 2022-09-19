package com.zagvladimir.mappers;


import com.zagvladimir.domain.User;

import com.zagvladimir.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface UserMapper {
    UserDTO toDTO(User user);
}
