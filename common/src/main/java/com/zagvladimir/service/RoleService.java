package com.zagvladimir.service;

import com.zagvladimir.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    List<Role> findRolesByUserId(Long roleId);

    Optional<Role> findRoleById(Long roleId);

    Role create(Role role);

    Long delete(Long id);

}
