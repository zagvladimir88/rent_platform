package com.zagvladimir.service.impl;

import com.zagvladimir.domain.User;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Map<String, Object> getUserStats() {
        return null;
    }

    @Transactional
    @Override
    public User create(User object) {
        return userRepository.save(object);
    }

    @Transactional
    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } return null ;
    }

    @Transactional
    @Override
    public User update(User object) {
        return userRepository.save(object);
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public List<User> search(int limit, int offset) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByUserLogin(login);
    }

    @Override
    public int createRoleRow(Long userId, Long roleId) {
        return userRepository.createRoleRow(userId, roleId);
    }
}