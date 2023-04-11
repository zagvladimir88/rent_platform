package com.zagvladimir.service.role;

import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

  Page<RoleResponse> findAll(Pageable page);

  List<RoleResponse> findRolesByUserId(Long roleId);

  RoleResponse findRoleById(Long roleId);

  RoleResponse create(RoleCreateRequest request);

  Long delete(Long id);
}
