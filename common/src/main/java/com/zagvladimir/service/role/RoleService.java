package com.zagvladimir.service.role;

import com.zagvladimir.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

  Page<Role> findAll(Pageable page);

  List<Role> findRolesByUserId(Long roleId);

  Role findRoleById(Long roleId);

  Role create(Role role);

  Long delete(Long id);
}
