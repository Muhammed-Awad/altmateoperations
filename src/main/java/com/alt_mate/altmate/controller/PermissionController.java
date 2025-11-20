package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.Permission;
import com.alt_mate.altmate.model.PermissionCategory;
import com.alt_mate.altmate.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    
    private final PermissionService permissionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Permission>> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Permission created successfully", createdPermission));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success(permission));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Permission>>> getPermissionsByCategory(@PathVariable PermissionCategory category) {
        List<Permission> permissions = permissionService.getPermissionsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> updatePermission(
            @PathVariable Long id,
            @RequestBody Permission permissionDetails) {
        Permission updatedPermission = permissionService.updatePermission(id, permissionDetails);
        return ResponseEntity.ok(ApiResponse.success("Permission updated successfully", updatedPermission));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success("Permission deleted successfully", null));
    }
}
