package com.alt_mate.altmate.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alt_mate.altmate.exception.BadRequestException;
import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.model.UserRole;
import com.alt_mate.altmate.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User user = getUserById(userId);
        user.setFullname(userDetails.getFullname());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setIsActive(userDetails.getIsActive());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
    
    @Transactional
    public int deleteUsers(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new BadRequestException("User IDs list cannot be empty");
        }
        
        int deletedCount = 0;
        for (Long userId : userIds) {
            try {
                User user = getUserById(userId);
                userRepository.delete(user);
                deletedCount++;
            } catch (RuntimeException e) {
                // Skip users that don't exist
                continue;
            }
        }
        return deletedCount;
    }
    
    @Transactional
    public int deleteAllUsers() {
        long count = userRepository.count();
        userRepository.deleteAll();
        return (int) count;
    }
    
    @Transactional
    public User deactivateUser(Long userId) {
        User user = getUserById(userId);
        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Transactional
    public User activateUser(Long userId) {
        User user = getUserById(userId);
        user.setIsActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
    
    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken, LocalDateTime expiryDate) {
        User user = getUserById(userId);
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiryDate(expiryDate);
        userRepository.save(user);
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> getActiveUsers() {
        return userRepository.findByIsActive(true);
    }
    
    public List<User> getUsersByRoleAndStatus(UserRole role, Boolean isActive) {
        return userRepository.findByRoleAndIsActive(role, isActive);
    }
    
    public List<User> getUsersCreatedBy(Long creatorId) {
        return userRepository.findByCreatedBy(creatorId);
    }
    
    public List<User> searchUsersByName(String name) {
        return userRepository.searchByName(name);
    }
    
    public List<User> searchUsersByEmail(String email) {
        return userRepository.searchByEmail(email);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Transactional
    public User updateUserRoles(Long userId, List<UserRole> roles) {
        User user = getUserById(userId);
        
        if (roles != null && !roles.isEmpty()) {
            // Set the primary role (first in the list)
            user.setRole(roles.get(0));
            
            // Store all roles in the roles set
            user.getRoles().clear();
            user.getRoles().addAll(roles);
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
