package com.zagvladimir.repository.user;

import com.zagvladimir.domain.User;
import com.zagvladimir.repository.CRUDRepository;
import java.util.Map;
import java.util.Optional;

public interface UserRepositoryInterface extends CRUDRepository<Long, User> {

  Map<String, Object> getUserStats();

  Optional<User> findByLogin(String login);
}
