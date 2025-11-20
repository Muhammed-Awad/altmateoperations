package com.example.altmate_operations.service;

import com.example.altmate_operations.exception.BadRequestException;
import com.example.altmate_operations.model.Permission;
import com.example.altmate_operations.model.PermissionCategory;
import com.example.altmate_operations.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    
    private final PermissionRepository permissionRepository;
    
    @Transactional
    public Permission createPermission(Permission permission) {
        if (permissionRepository.existsByName(permission.getName())) {
            throw new BadRequestException("Permission with name " + permission.getName() + " already exists");
        }
        return permissionRepository.save(permission);
    }
    
    @Transactional
    public Permission updatePermission(Long permissionId, Permission permissionDetails) {
        Permission permission = getPermissionById(permissionId);
        permission.setName(permissionDetails.getName());
        permission.setDescription(permissionDetails.getDescription());
        permission.setCategory(permissionDetails.getCategory());
        return permissionRepository.save(permission);
    }
    
    @Transactional
    public void deletePermission(Long permissionId) {
        Permission permission = getPermissionById(permissionId);
        permissionRepository.delete(permission);
    }
    
    public Permission getPermissionById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionId));
    }
    
    public Permission getPermissionByName(String name) {
        return permissionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Permission not found with name: " + name));
    }
    
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAllOrderByCategoryAndName();
    }
    
    public List<Permission> getPermissionsByCategory(PermissionCategory category) {
        return permissionRepository.findByCategory(category);
    }
    
    public List<Permission> searchPermissionsByName(String name) {
        return permissionRepository.searchByName(name);
    }
    
    public boolean existsByName(String name) {
        return permissionRepository.existsByName(name);
    }
}
