package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.domain.Role;

import com.zagvladimir.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleRepository roleRepository;


    @Operation(summary = "Gets all roles")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the roles",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllRoles() {
        return new ResponseEntity<>(Collections.singletonMap("result", roleRepository.findAll()),
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
                                            array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                            })
            })
    @GetMapping("users/{id}")
    public ResponseEntity<Object> findRolesByUserId(@PathVariable Long id){
        return new ResponseEntity<>(Collections.singletonMap("result", roleRepository.findRolesByUserid(id)),
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
                                            array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findRoleById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        return new ResponseEntity<>(Collections.singletonMap("role", roleRepository.findById(userId)), HttpStatus.OK);
    }


    @Operation(
            summary = "Create new Role",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Role create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
                    @ApiResponse( responseCode = "409", description = "Role not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Role not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleCreateRequest createRequest) {
        Role role = new Role();

        role.setName(createRequest.getName());
        role.setCreationDate(new Timestamp(new Date().getTime()));
        role.setModificationDate(new Timestamp(new Date().getTime()));
        role.setStatus(createRequest.getStatus());

        roleRepository.save(role);

        List<Role> roles = roleRepository.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("roles",roles);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete Role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable Long id){

        roleRepository.deleteById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("role has been deleted id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
