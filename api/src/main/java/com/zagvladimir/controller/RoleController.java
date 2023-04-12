package com.zagvladimir.controller;

import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
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

@Tag(name = "Roles controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleService roleService;


  @Operation(summary = "Gets all roles")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the roles", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))})})
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> findAllRoles(@ParameterObject Pageable page) {
    return new ResponseEntity<>(roleService.findAll(page), HttpStatus.OK);
  }

  @Operation(summary = "Gets roles by user id")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the roles", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))})})
  @GetMapping("users/{id}")
  public ResponseEntity<Object> findRolesByUserId(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    return new ResponseEntity<>(roleService.findRolesByUserId(userId), HttpStatus.OK);
  }

  @Operation(summary = "Gets role by ID")
      @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the role by id", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))})})
  @GetMapping("/{roleId}")
  public ResponseEntity<Object> findRoleById(@PathVariable Long roleId) {
    return new ResponseEntity<>(roleService.findRoleById(roleId), HttpStatus.OK);
  }

  @Operation(summary = "Create new Role")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role create successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))),
        @ApiResponse(responseCode = "409", description = "Role not created, Conflict", content = @Content),
        @ApiResponse(responseCode = "500", description = "Role not created, Illegal Arguments", content = @Content)})
 @PostMapping
  public ResponseEntity<Object> createRole(@RequestBody @Valid RoleCreateRequest createRequest) {
    return new ResponseEntity<>(roleService.create(createRequest), HttpStatus.CREATED);
  }
}
