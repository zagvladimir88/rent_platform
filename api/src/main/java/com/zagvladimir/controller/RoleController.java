package com.zagvladimir.controller;

import com.zagvladimir.mappers.RoleMapper;
import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
import com.zagvladimir.domain.Role;
import com.zagvladimir.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Roles controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleService roleService;
  private final RoleMapper roleMapper;

  @Operation(
      summary = "Gets all roles",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the roles",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Object> findAllRoles(@ParameterObject Pageable page) {
    Page<RoleResponse> roleResponseList = roleService.findAll(page).map(roleMapper::toResponse);
    return new ResponseEntity<>(roleResponseList, HttpStatus.OK);
  }

  @Operation(
      summary = "Gets roles by user id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the roles",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping("users/{id}")
  public ResponseEntity<Object> findRolesByUserId(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    List<RoleResponse> roleResponseList =
        roleService.findRolesByUserId(userId).stream()
            .map(roleMapper::toResponse)
            .collect(Collectors.toList());
    return new ResponseEntity<>(Collections.singletonMap("roles", roleResponseList), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets role by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the role by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping("/{roleId}")
  public ResponseEntity<Map<String, Object>> findRoleById(@PathVariable Long roleId) {

    RoleResponse roleResponse = roleMapper.toResponse(roleService.findRoleById(roleId));

    return new ResponseEntity<>(Collections.singletonMap("role", roleResponse), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new Role",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Role create successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Role not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Role not created, Illegal Arguments",
            content = @Content)
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Object> createRole(@RequestBody @Valid RoleCreateRequest createRequest) {
    Role role = roleService.create(roleMapper.convertCreateRequest(createRequest));
    return new ResponseEntity<>(roleMapper.toResponse(role), HttpStatus.CREATED);
  }
}
