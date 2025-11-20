package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.TaskPriority;
import com.example.altmate_operations.model.TaskStatus;
import com.example.altmate_operations.model.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequest {
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Task type is required")
    private TaskType type;
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;
    
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;
    
    @NotNull(message = "Task status is required")
    private TaskStatus status;
    
    private TaskPriority priority;
    
    private LocalDateTime dueDate;
    
    private Long assignedToId;
    
    private Long assignedById;
    
    private String designBrief;
    
    private List<String> referenceImages;
    
    private String moodBoard;
    
    private LocalDateTime shootDate;
}
