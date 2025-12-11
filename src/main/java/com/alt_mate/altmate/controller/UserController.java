package com.alt_mate.altmate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.DTO.BulkDeleteRequest;
import com.alt_mate.altmate.DTO.ChangePasswordRequest;
import com.alt_mate.altmate.DTO.UserCreateRequest;
import com.alt_mate.altmate.DTO.UserDTO;
import com.alt_mate.altmate.DTO.UserUpdateRequest;
import com.alt_mate.altmate.mapper.UserMapper;
import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.model.UserRole;
import com.alt_mate.altmate.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserMapper userMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = userMapper.toEntity(request);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", userMapper.toDTO(createdUser)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(user)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTOList(users)));
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTOList(users)));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTOList(users)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        User existingUser = userService.getUserById(id);
        userMapper.updateEntityFromDTO(request, existingUser);
        User updatedUser = userService.updateUser(id, existingUser);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", userMapper.toDTO(updatedUser)));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserDTO>> deactivateUser(@PathVariable Long id) {
        User user = userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated successfully", userMapper.toDTO(user)));
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<UserDTO>> activateUser(@PathVariable Long id) {
        User user = userService.activateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User activated successfully", userMapper.toDTO(user)));
    }
    
    @PutMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
    
    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<String>> deleteUsers(@Valid @RequestBody BulkDeleteRequest request) {
        int deletedCount = userService.deleteUsers(request.getUserIds());
        return ResponseEntity.ok(ApiResponse.success(
                deletedCount + " user(s) deleted successfully", 
                null
        ));
    }
    
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<String>> deleteAllUsers() {
        int deletedCount = userService.deleteAllUsers();
        return ResponseEntity.ok(ApiResponse.success(
                "All users deleted successfully. Total: " + deletedCount, 
                null
        ));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> searchUsers(@RequestParam String name) {
        List<User> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTOList(users)));
    }
    
    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserRoles(
            @PathVariable Long id,
            @RequestBody List<UserRole> roles) {
        User user = userService.updateUserRoles(id, roles);
        return ResponseEntity.ok(ApiResponse.success("User roles updated successfully", userMapper.toDTO(user)));
    }
}
