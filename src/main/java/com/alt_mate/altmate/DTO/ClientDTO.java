package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.ClientIndustry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private ClientIndustry industry;
    private String description;
    private String logo;
    private Boolean isActive;
    private List<UserDTO> assignedUsers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
