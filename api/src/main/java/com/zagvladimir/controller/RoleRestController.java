package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.role.RoleCreateRequest;
import com.zagvladimir.domain.Role;

import com.zagvladimir.repository.RoleRepository;
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

    @GetMapping
    public ResponseEntity<Object> findAllRoles() {
        return new ResponseEntity<>(Collections.singletonMap("result", roleRepository.findAll()),
                HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Object> findRolesByUserId(@PathVariable Long id){
        return new ResponseEntity<>(Collections.singletonMap("result", roleRepository.findRolesByUserid(id)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findRoleById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        return new ResponseEntity<>(Collections.singletonMap("role", roleRepository.findById(userId)), HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable Long id){

        roleRepository.deleteById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("role has been deleted id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
