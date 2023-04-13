package com.zagvladimir.service.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.dto.requests.role.RoleCreateRequest;
import com.zagvladimir.dto.response.RoleResponse;
import com.zagvladimir.mappers.RoleMapper;
import com.zagvladimir.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Page<RoleResponse> findAll(Pageable page) {
        return roleRepository.findAll(page)
                .map(roleMapper::toResponse);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        Optional<Role> roleByName = Optional.ofNullable(roleRepository.findRoleByName(name));
        if (roleByName.isPresent()) {
            return roleByName;
        }
        throw new EntityNotFoundException(String.format("The role with name: %s not found", name));
    }

    @Override
    public List<RoleResponse> findRolesByUserId(Long roleId) {
        return roleRepository.findRolesByUserid(roleId)
                .stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse findRoleById(Long roleId) {
        return roleRepository
                .findById(roleId)
                .map(roleMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("The role with id:%d not found", roleId)));
    }

    @Transactional
    @Override
    public RoleResponse create(RoleCreateRequest request) {
        Role role = roleMapper.convertCreateRequest(request);
        roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        roleRepository.deleteById(id);
        return id;
    }
}

