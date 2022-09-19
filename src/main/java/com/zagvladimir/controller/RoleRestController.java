package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.domain.Role;
import com.zagvladimir.mappers.RoleListMapper;
import com.zagvladimir.mappers.RoleMapper;
import com.zagvladimir.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/roles")
public class RoleRestController {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleListMapper roleListMapper;

    @GetMapping
    public ResponseEntity<Object> findAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return new ResponseEntity<>(Collections.singletonMap("result", roleListMapper.toDTOs(roleList)),
                HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Object> findRolesByUserId(@PathVariable Long id){
        List<Role> roleList = roleRepository.findRolesByUserId(id);
        return new ResponseEntity<>(Collections.singletonMap("result", roleListMapper.toDTOs(roleList)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findRoleById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        Role role = roleRepository.findById(userId);
        return new ResponseEntity<>(Collections.singletonMap("role", roleMapper.toDTO(role)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleCreateRequest createRequest) {
        Role role = new Role();

        role.setName(createRequest.getName());
        role.setCreationDate(new Timestamp(new Date().getTime()));
        role.setModificationDate(new Timestamp(new Date().getTime()));
        role.setStatus(createRequest.getStatus());

        roleRepository.create(role);

        List<Role> roles = roleRepository.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("roles", roleListMapper.toDTOs(roles));

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable Long id){

        roleRepository.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("role has been deleted id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
