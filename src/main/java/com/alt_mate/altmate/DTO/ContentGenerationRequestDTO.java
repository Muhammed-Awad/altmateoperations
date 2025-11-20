package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.GenerationStatus;
import com.alt_mate.altmate.model.ResponseStyle;
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
