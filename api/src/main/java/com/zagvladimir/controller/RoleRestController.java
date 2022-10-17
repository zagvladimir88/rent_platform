package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.RoleMapper;
import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.controller.response.RoleResponse;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Roles controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

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
    public ResponseEntity<Object> findAllRoles() {
        return new ResponseEntity<>(Collections.singletonMap("result", roleService.findAll().stream().map(roleMapper::toRoleResponse)),
                HttpStatus.OK);
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
    public ResponseEntity<Object> findRolesByUserId(@PathVariable Long id){
        return new ResponseEntity<>(Collections.singletonMap("result", roleService.findRolesByUserId(id).stream().map(roleMapper::toRoleResponse)),
                HttpStatus.OK);
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

        return new ResponseEntity<>(Collections.singletonMap("role",roleMapper.toRoleResponse(roleService.findRoleById(roleId).get())), HttpStatus.OK);
    }


    @Operation(
            summary = "Create new Role",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Role create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))),
                    @ApiResponse( responseCode = "409", description = "Role not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Role not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleCreateRequest createRequest) {
        roleService.create(roleMapper.fromCreateRequest(createRequest));

        return new ResponseEntity<>(roleRepository.findAll().stream().map(roleMapper::toRoleResponse), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete Role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable Long id){

        roleService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("role has been deleted id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
