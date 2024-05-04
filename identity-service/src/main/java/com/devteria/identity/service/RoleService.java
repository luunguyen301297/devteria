package com.devteria.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devteria.identity.dto.request.RoleRequest;
import com.devteria.identity.dto.response.RoleResponse;
import com.devteria.identity.mapper.RoleMapper;
import com.devteria.identity.repository.PermissionRepository;
import com.devteria.identity.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepo;
    PermissionRepository permissionRepo;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toEntity(request);

        var permissions = permissionRepo.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepo.save(role);
        return roleMapper.toDto(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepo.findAll().stream().map(roleMapper::toDto).toList();
    }

    public void delete(String role) {
        roleRepo.deleteById(role);
    }

}
