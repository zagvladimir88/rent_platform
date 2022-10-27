package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.RoleMapper;
import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.controller.response.RoleResponse;
import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Operation(summary = "Gets all roles")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the roles",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      })
  @GetMapping
  public ResponseEntity<Object> findAllRoles(@ParameterObject Pageable page) {
    Page<RoleResponse> roleResponseList = roleService.findAll(page).map(roleMapper::toResponse);
    return new ResponseEntity<>(roleResponseList, HttpStatus.OK);
  }

  @Operation(summary = "Gets roles by user id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the roles",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      })
  @GetMapping("users/{id}")
  public ResponseEntity<Object> findRolesByUserId(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    List<RoleResponse> roleResponseList =
        roleService.findRolesByUserId(userId).stream()
            .map(roleMapper::toResponse)
            .collect(Collectors.toList());
    return new ResponseEntity<>(Collections.singletonMap("roles", roleResponseList), HttpStatus.OK);
  }

  @Operation(summary = "Gets role by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the role by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))
            })
      })
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
      })
  @PostMapping
  public ResponseEntity<Object> createRole(@RequestBody RoleCreateRequest createRequest) {
    Role role = roleService.create(roleMapper.convertCreateRequest(createRequest));
    return new ResponseEntity<>(roleMapper.toResponse(role), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Delete Role",
      responses = {
        @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRoleById(@PathVariable Long id) {
    roleService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("role has been deleted id:", id), HttpStatus.OK);
  }
}
