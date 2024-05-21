package com.devteria.identity.service;

import java.util.HashSet;
import java.util.List;

import com.devteria.identity.mapper.UserProfileMapper;
import com.devteria.identity.repository.http_client.UserProfileClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devteria.identity.constant.ROLE;
import com.devteria.identity.dto.request.UserCreationRequest;
import com.devteria.identity.dto.request.UserUpdateRequest;
import com.devteria.identity.dto.response.UserResponse;
import com.devteria.identity.entity.Role;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ERROR_CODE;
import com.devteria.identity.mapper.UserMapper;
import com.devteria.identity.repository.RoleRepository;
import com.devteria.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepo;
    RoleRepository roleRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    UserProfileClient profileClient;
    UserProfileMapper profileMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepo.existsByUsername(request.getUsername()))
            throw new AppException(ERROR_CODE.USER_EXISTED);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepo.findById(ROLE.USER.val).ifPresent(roles::add);

        user.setRoles(roles);
        user = userRepo.save(user);

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        profileClient.createProfile(profileRequest);

        return userMapper.toDto(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepo.findByUsername(name).orElseThrow(()
                -> new AppException(ERROR_CODE.USER_NOT_EXISTED));

        return userMapper.toDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepo.findById(userId).orElseThrow(()
                -> new AppException(ERROR_CODE.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepo.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toDto(userRepo.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepo.findAll().stream().map(userMapper::toDto).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        User user = userRepo.findById(id).orElseThrow(()
                -> new AppException(ERROR_CODE.USER_NOT_EXISTED));

        return userMapper.toDto(user);
    }

}
