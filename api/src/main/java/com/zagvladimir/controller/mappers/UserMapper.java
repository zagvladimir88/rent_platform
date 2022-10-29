package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.response.UserResponse;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {LocationMapper.class,RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "location", target = "userLocation")
    @Mapping(source = "roles", target = "userRoles")
    UserResponse toResponse(User user);

    @Mapping(source = "userPassword",target = "credentials.userPassword")
    @Mapping(source = "userLogin",target = "credentials.userLogin")
    @Mapping(source = "email",target = "credentials.email")
    User convertCreateRequest(UserCreateRequest userCreateRequest);

    User convertUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}
