package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.GenerationStatus;
import com.example.altmate_operations.model.ResponseStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentGenerationRequestDTO {
    
    private Long id;
    private String prompt;
    private ResponseStyle tone;
    private String targetAudience;
    private GenerationStatus status;
    private String generatedContent;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
    
    // Requested by relationship
    private Long requestedById;
    private String requestedByName;
}
