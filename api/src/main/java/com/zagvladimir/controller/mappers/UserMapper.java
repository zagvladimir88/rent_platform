package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.response.user.UserResponse;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "roles", target = "userRoles")
    UserResponse toResponse(User user);

    @Mapping(source = "userPassword",target = "credentials.userPassword")
    @Mapping(source = "userLogin",target = "credentials.userLogin")
    @Mapping(source = "email",target = "credentials.email")
    @Mapping(source = "addressLine1",target = "address.addressLine1")
    @Mapping(source = "addressLine2",target = "address.addressLine2")
    @Mapping(source = "state",target = "address.state")
    @Mapping(source = "city",target = "address.city")
    @Mapping(source = "postalCode",target = "address.postalCode")
    User convertCreateRequest(UserCreateRequest userCreateRequest);

    User convertUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}
