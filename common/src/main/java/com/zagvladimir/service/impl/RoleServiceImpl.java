package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findRolesByUserId(Long roleId) {
        return roleRepository.findRolesByUserid(roleId);
    }

    @Override
    public Optional<Role> findRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public Role create(Role role) {
        role.setCreationDate(new Timestamp(new Date().getTime()));
        role.setModificationDate(role.getCreationDate());

        return roleRepository.save(role);
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
