package com.alt_mate.altmate.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import com.alt_mate.altmate.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String fullname;
    private String email;
    private UserRole role;
    private Set<UserRole> roles;
    private Boolean isActive;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
