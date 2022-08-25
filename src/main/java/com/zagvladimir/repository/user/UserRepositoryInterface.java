package com.zagvladimir.repository.user;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.CRUDRepository;
import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long,User> {

  Map<String, Object> getUserStats();
}
