package com.zagvladimir.service;

import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    @Autowired
//    @Qualifier("userRepository")

    //    @Inject
//    @Named("userRepository")
//JSR-330
    private final UserRepositoryInterface userRepository;

//    public UserServiceImpl(@Qualifier("userRepository") UserRepositoryInterface userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Map<String, Object> getUserStats() {
        return userRepository.getUserStats();
    }

    @Override
    public User create(User object) {
        return userRepository.create(object);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User update(User object) {
        return userRepository.update(object);
    }

    @Override
    public Long delete(Long id) {
        return userRepository.delete(id);
    }


//    @Autowired
//    //@Inject
//    public void setUserRepository(@Qualifier("userRepository") UserRepositoryInterface userRepository) {
//        this.userRepository = userRepository;
//    }
}