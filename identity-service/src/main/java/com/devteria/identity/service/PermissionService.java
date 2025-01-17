package com.devteria.identity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devteria.identity.dto.request.PermissionRequest;
import com.devteria.identity.dto.response.PermissionResponse;
import com.devteria.identity.entity.Permission;
import com.devteria.identity.mapper.PermissionMapper;
import com.devteria.identity.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepo;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toEntity(request);
        permission = permissionRepo.save(permission);

        return permissionMapper.toDto(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepo.findAll();

        return permissions.stream().map(permissionMapper::toDto).toList();
    }

    public void delete(String permission) {
        permissionRepo.deleteById(permission);
    }

}
