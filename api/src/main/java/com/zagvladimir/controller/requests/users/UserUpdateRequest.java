package com.zagvladimir.controller.requests.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {

  @Schema(defaultValue = "Vladimir", type = "string" , description = "User Name")
  @NotBlank
  @Size(min = 2,max = 25)
  private String firstName;

  @Schema(defaultValue = "Ivanov", type = "string" , description = "Last Name")
  @NotBlank
  @Size(min = 2,max = 32)
  private String lastName;

  @Schema(defaultValue = "testLogin", type = "string" , description = "User Login")
  @NotBlank
  @Size(min = 2,max = 100)
  private String userLogin;

  @Schema(defaultValue = "testPassword", type = "string" , description = "User Password")
  @NotBlank
  @Size(min = 6,max = 200)
  private String userPassword;

  @Size(min = 2,max = 200)
  @Schema(defaultValue = "Microdistrict 17", type = "string" , description = "User address first line")
  private String addressLine1;

  @Size(min = 2,max = 200)
  @Schema(defaultValue = "House 5-2", type = "string" , description = "User address second line")
  private String addressLine2;

  @Size(min = 2,max = 25)
  @Schema(defaultValue = "Gomelskaya", type = "string" , description = "State")
  private String state;

  @Size(min = 2,max = 25)
  @Schema(defaultValue = "Zhlobin", type = "string" , description = "City")
  private String city;

  @Size(min = 2,max = 10)
  @Schema(defaultValue = "247210", type = "string" , description = "Postal code")
  private String postalCode;

  @Schema(defaultValue = "+375291544444", type = "string" , description = "User mobile number")
  private String mobileNumber;

  @Size(min = 2, max = 255)
  @Schema(defaultValue = "test@gmail.com", type = "string" , description = "User email")
  @Email
  private String email;
}
