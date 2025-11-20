package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.PermissionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    
    private Long id;
    private String name;
    private String description;
    private PermissionCategory category;
}
