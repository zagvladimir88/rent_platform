package com.zagvladimir.controller.mappers;


import com.zagvladimir.controller.response.UserResponse;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(uses = {LocationMapper.class,RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "location", target = "userLocation")
    @Mapping(source = "roles", target = "userRoles")
    UserResponse userToUserResponse(User user);

    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

    User updateUserFromUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}