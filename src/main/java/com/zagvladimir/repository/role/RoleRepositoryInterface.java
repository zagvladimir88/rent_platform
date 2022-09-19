package com.zagvladimir.repository.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.CRUDRepository;

import java.util.List;

public interface RoleRepositoryInterface extends CRUDRepository<Long, Role> {
    List<Role> findRolesByUserId(Long userId);
}
