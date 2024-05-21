package com.devteria.identity.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.devteria.identity.dto.response.ApiResponse;
import com.devteria.identity.dto.request.RoleRequest;
import com.devteria.identity.dto.response.RoleResponse;
import com.devteria.identity.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<?> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<String>builder()
                .data("success deleted")
                .build();
    }

}
