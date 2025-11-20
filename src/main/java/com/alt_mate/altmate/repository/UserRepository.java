package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByRole(UserRole role);
    
    List<User> findByIsActive(Boolean isActive);
    
    List<User> findByRoleAndIsActive(UserRole role, Boolean isActive);
    
    @Query("SELECT u FROM User u WHERE u.createdby.id = :creatorId")
    List<User> findByCreatedBy(@Param("creatorId") Long creatorId);
    
    @Query("SELECT u FROM User u WHERE u.fullname LIKE %:name%")
    List<User> searchByName(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> searchByEmail(@Param("email") String email);
    
    Optional<User> findByRefreshToken(String refreshToken);
}
