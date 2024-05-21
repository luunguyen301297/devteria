package com.devteria.profile.service;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {

    UserProfileRepository userProfileRepo;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(UserProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toEntity(request);

        userProfile = userProfileRepo.save(userProfile);

        return userProfileMapper.toDto(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getAllProfiles() {
        var profiles = userProfileRepo.findAll();

        return profiles.stream().map(userProfileMapper::toDto).toList();
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile = userProfileRepo.findById(id).orElseThrow(()
                -> new RuntimeException("User profile not found"));

        return userProfileMapper.toDto(userProfile);
    }

}
