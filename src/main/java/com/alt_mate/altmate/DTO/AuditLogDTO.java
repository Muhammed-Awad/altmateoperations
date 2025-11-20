package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    
    private Long id;
    private AuditAction action;
    private String entityType;
    private Long entityId;
    private String description;
    private String ipAddress;
    private LocalDateTime timestamp;
    
    // User relationship
    private Long userId;
    private String userName;
}
