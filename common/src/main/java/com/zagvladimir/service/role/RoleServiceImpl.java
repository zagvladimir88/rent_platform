package com.zagvladimir.service.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Page<Role> findAll(Pageable page) {
    return roleRepository.findAll(page);
  }

  @Override
  public List<Role> findRolesByUserId(Long roleId) {
    return roleRepository.findRolesByUserid(roleId);
  }

  @Override
  public Role findRoleById(Long roleId) {
    return roleRepository
        .findById(roleId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The role with id:%d not found", roleId)));
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
