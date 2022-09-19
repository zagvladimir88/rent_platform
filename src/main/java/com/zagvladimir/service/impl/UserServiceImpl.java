package com.zagvladimir.service.impl;

import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryInterface userRepository;

    @Transactional
    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Map<String, Object> getUserStats() {
        return userRepository.getUserStats();
    }

    @Transactional
    @Override
    public User create(User object) {
        return userRepository.create(object);
    }

    @Transactional
    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public User update(User object) {
        return userRepository.update(object);
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        return userRepository.delete(id);
    }

    @Transactional
    @Override
    public List<User> search(int limit, int offset) {
        return userRepository.findAll(limit, offset);
    }
}