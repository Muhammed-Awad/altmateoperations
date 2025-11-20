package com.alt_mate.altmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRevisionDTO {
    
    private Long id;
    private String revisionNotes;
    private String revisionInstructions;
    private LocalDateTime requestedAt;
    
    // Task relationship
    private Long taskId;
    private String taskTitle;
    
    // Requested by relationship
    private Long requestedById;
    private String requestedByName;
}
