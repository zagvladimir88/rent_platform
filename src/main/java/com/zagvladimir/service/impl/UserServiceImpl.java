package com.zagvladimir.service.impl;

import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryInterface userRepository;

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

    @Override
    public List<User> search(int limit, int offset) {
        return userRepository.findAll(limit, offset);
    }
}