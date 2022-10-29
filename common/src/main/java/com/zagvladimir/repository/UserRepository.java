package com.zagvladimir.repository;

import com.zagvladimir.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByCredentialsUserLogin(String login);
    Optional<User> findUsersByCredentials_Email(String email);
    Optional<User> findUsersByActivationCode(String code);



}
