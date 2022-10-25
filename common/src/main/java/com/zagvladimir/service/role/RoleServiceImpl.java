package com.zagvladimir.service.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  @Override
  public Role create(Role role) {
    return roleRepository.save(role);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    roleRepository.deleteById(id);
    return id;
  }
}
