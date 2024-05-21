package com.devteria.identity.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.devteria.identity.constant.PERMISSION;
import com.devteria.identity.entity.Permission;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devteria.identity.constant.ROLE;
import com.devteria.identity.entity.Role;
import com.devteria.identity.entity.User;
import com.devteria.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncode;

    @Bean
    ApplicationRunner runner(UserRepository userRepo) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {

                Set<Permission> permissions = createAdminPermissions();

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncode.encode("admin@123"))
                        .roles(createAdminRoles(permissions))
                        .build();

                userRepo.save(user);
                log.warn("admin has been created with default password: admin@123");
            }
        };
    }

    private static Set<Permission> createAdminPermissions() {
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
        return new HashSet<>(List.of(permissionCreate, permissionApprove, permissionReject));
    }

    private static Set<Role> createAdminRoles(Set<Permission> permissions) {
        Role role = Role.builder()
                .name(ROLE.ADMIN.val)
                .description("Tao la admin")
                .permissions(permissions)
                .build();
        return new HashSet<>(List.of(role));
    }

}
