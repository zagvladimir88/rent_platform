package com.zagvladimir.controller.mappers;


import com.zagvladimir.controller.response.UserResponse;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper
public interface UserMapper {

    @Mapping(target = "location.items", ignore = true)
    @Mapping(target = "location.users", ignore = true)
    UserResponse userToUserResponse(User user);

    @Mapping(target = "location.items", ignore = true)
    @Mapping(target = "location.users", ignore = true)
    List<UserResponse> toListUserResponse(List<User> userList);

    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

    User userUpdateRequestToUser(UserUpdateRequest userUpdateRequest);

    User updateUserFromUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}
