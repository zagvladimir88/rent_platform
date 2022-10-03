package com.zagvladimir.repository;

import com.zagvladimir.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserLogin(String login);

    Page<User> findAll(Pageable page);

    @Modifying
    @Query(value = "insert into user_roles(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    int createRoleRow(@Param("user_id") Long userId, @Param("role_id") Long roleId);

}
