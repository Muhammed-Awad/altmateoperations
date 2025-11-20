package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientComplaintDTO {
    
    private Long id;
    private String subject;
    private String description;
    private ComplaintStatus status;
    private String resolution;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
    
    // Assigned user relationship
    private Long assignedToId;
    private String assignedToName;
}
