package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.Permission;
import com.example.altmate_operations.model.PermissionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Optional<Permission> findByName(String name);
    
    List<Permission> findByCategory(PermissionCategory category);
    
    @Query("SELECT p FROM Permission p WHERE p.name LIKE %:name%")
    List<Permission> searchByName(@Param("name") String name);
    
    @Query("SELECT p FROM Permission p ORDER BY p.category, p.name")
    List<Permission> findAllOrderByCategoryAndName();
    
    boolean existsByName(String name);
}
