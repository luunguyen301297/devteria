package com.devteria.identity.mapper;

import org.mapstruct.Mapper;

import com.devteria.identity.dto.request.PermissionRequest;
import com.devteria.identity.dto.response.PermissionResponse;
import com.devteria.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity(PermissionRequest request);

    PermissionResponse toDto(Permission permission);
}
