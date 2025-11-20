package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullname;
    
    @Email(message = "Email must be valid")
    private String email;
    
    private UserRole role;
    
    private Boolean isActive;
}
