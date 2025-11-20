package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.ClientIndustry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateRequest {
    
    @NotBlank(message = "Client name is required")
    @Size(min = 2, max = 100, message = "Client name must be between 2 and 100 characters")
    private String name;
    
    private ClientIndustry industry;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    private String logo;
    
    private Boolean isActive = true;
    
    private List<Long> assignedUserIds;
}
