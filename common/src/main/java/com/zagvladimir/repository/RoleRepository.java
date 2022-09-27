package com.zagvladimir.repository;

import com.zagvladimir.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("select r from Role r inner join r.users u where u.id = :userId")
    List<Role> findRolesByUserid(Long userId);

    Role findRoleByName(String name);
}
