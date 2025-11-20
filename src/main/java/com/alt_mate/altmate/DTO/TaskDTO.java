package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.TaskPriority;
import com.example.altmate_operations.model.TaskStatus;
import com.example.altmate_operations.model.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private TaskType type;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private Long assignedToId;
    private String assignedToName;
    private Long assignedById;
    private String assignedByName;
    private String designBrief;
    private List<String> referenceImages;
    private String moodBoard;
    private LocalDateTime shootDate;
    private List<String> deliverableUrls;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private LocalDateTime completedAt;
}
