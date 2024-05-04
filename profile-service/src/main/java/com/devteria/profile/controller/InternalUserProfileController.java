package com.devteria.profile.controller;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.ApiResponse;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/internal/users")
public class InternalUserProfileController {

    UserProfileService userProfileService;

    @PostMapping("/create")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody UserProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.createProfile(request))
                .build();
    }

}
