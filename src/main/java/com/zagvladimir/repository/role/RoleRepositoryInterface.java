package com.zagvladimir.repository.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.CRUDRepository;


import java.util.Set;

public interface RoleRepositoryInterface extends CRUDRepository<Long, Role> {
    Set<Role> findRolesByUserId(Long userId);
}
