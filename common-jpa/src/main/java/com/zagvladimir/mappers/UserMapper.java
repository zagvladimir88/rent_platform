package com.zagvladimir.mappers;


import com.zagvladimir.domain.user.User;
import com.zagvladimir.dto.requests.auth.SignupRequest;
import com.zagvladimir.dto.requests.users.UserChangeAddressRequest;
import com.zagvladimir.dto.requests.users.UserChangeCredentialsRequest;
import com.zagvladimir.dto.requests.users.UserCreateRequest;
import com.zagvladimir.dto.requests.users.UserUpdateRequest;
import com.zagvladimir.dto.response.user.UserResponse;
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

    @Mapping(source = "userPassword",target = "credentials.userPassword")
    @Mapping(source = "userLogin",target = "credentials.userLogin")
    @Mapping(source = "email",target = "credentials.email")
    @Mapping(source = "addressLine1",target = "address.addressLine1")
    @Mapping(source = "addressLine2",target = "address.addressLine2")
    @Mapping(source = "state",target = "address.state")
    @Mapping(source = "city",target = "address.city")
    @Mapping(source = "postalCode",target = "address.postalCode")
    User convertUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);


    @Mapping(source = "userPassword",target = "credentials.userPassword")
    @Mapping(source = "userLogin",target = "credentials.userLogin")
    User convertUpdateRequest(UserChangeCredentialsRequest request, @MappingTarget User user);

    @Mapping(source = "addressLine1",target = "address.addressLine1")
    @Mapping(source = "addressLine2",target = "address.addressLine2")
    @Mapping(source = "state",target = "address.state")
    @Mapping(source = "city",target = "address.city")
    @Mapping(source = "postalCode",target = "address.postalCode")
    User convertUpdateRequest(UserChangeAddressRequest request, @MappingTarget User user);

    @Mapping(source = "userPassword",target = "credentials.userPassword")
    @Mapping(source = "userLogin",target = "credentials.userLogin")
    @Mapping(source = "email",target = "credentials.email")
    @Mapping(source = "addressLine1",target = "address.addressLine1")
    @Mapping(source = "addressLine2",target = "address.addressLine2")
    @Mapping(source = "state",target = "address.state")
    @Mapping(source = "city",target = "address.city")
    @Mapping(source = "postalCode",target = "address.postalCode")
    User convertSignUpRequest(SignupRequest signUpRequest);
}
