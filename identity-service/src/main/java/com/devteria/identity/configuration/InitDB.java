package com.devteria.identity.configuration;

import com.devteria.identity.constant.PERMISSION;
import com.devteria.identity.constant.ROLE;
import com.devteria.identity.entity.Permission;
import com.devteria.identity.entity.Role;
import com.devteria.identity.repository.PermissionRepository;
import com.devteria.identity.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitDB {

    RoleRepository roleRepo;
    PermissionRepository permissionRepo;

    @PostConstruct
    void initDBData() {
        Permission permissionCreate = Permission.builder()
                .name(PERMISSION.CREATE_POST.val)
                .description("create post")
                .build();
        Permission permissionApprove = Permission.builder()
                .name(PERMISSION.APPROVE_POST.val)
                .description("approve post")
                .build();
        Permission permissionReject = Permission.builder()
                .name(PERMISSION.REJECT_POST.val)
                .description("reject post")
                .build();

        permissionRepo.saveAll(List.of(permissionCreate, permissionApprove, permissionReject));

        Role roleAdmin = Role.builder()
                .name(ROLE.ADMIN.val)
                .description("Tao la admin")
                .permissions(new HashSet<>(List.of(permissionCreate, permissionApprove, permissionReject)))
                .build();

        Role roleUser = Role.builder()
                .name(ROLE.USER.val)
                .description("Tao la user")
                .permissions(new HashSet<>(List.of(permissionCreate)))
                .build();
        roleRepo.saveAll(List.of(roleAdmin, roleUser));
    }

}
