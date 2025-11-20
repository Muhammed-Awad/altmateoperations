package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.PermissionCategory;
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
