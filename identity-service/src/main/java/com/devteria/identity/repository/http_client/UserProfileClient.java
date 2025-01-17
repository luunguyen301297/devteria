package com.devteria.identity.repository.http_client;

import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.response.ApiResponse;
import com.devteria.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.service.profile}")
public interface UserProfileClient {

    @PostMapping(value = "/internal/users/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody UserProfileCreationRequest request);

}
