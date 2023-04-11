package com.zagvladimir.service.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {

  Page<RoleResponse> findAll(Pageable page);

  Optional<Role> findRoleByName(String name);

  List<RoleResponse> findRolesByUserId(Long roleId);

  RoleResponse findRoleById(Long roleId);

  RoleResponse create(RoleCreateRequest request);

  Long delete(Long id);
}
