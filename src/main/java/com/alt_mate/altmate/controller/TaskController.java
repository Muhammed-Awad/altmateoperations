package com.example.altmate_operations.controller;

import com.example.altmate_operations.DTO.*;
import com.example.altmate_operations.model.Task;
import com.example.altmate_operations.model.TaskStatus;
import com.example.altmate_operations.model.TaskType;
import com.example.altmate_operations.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@Valid @RequestBody TaskCreateRequest request) {
        Task task = mapToEntity(request);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created successfully", mapToDTO(createdTask)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(task)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByClient(@PathVariable Long clientId) {
        List<Task> tasks = taskService.getTasksByClient(clientId);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByType(@PathVariable TaskType type) {
        List<Task> tasks = taskService.getTasksByType(type);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/assigned/{userId}")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksAssignedToUser(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksAssignedToUser(userId);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getOverdueTasks() {
        List<Task> tasks = taskService.getOverdueTasks();
        List<TaskDTO> taskDTOs = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskCreateRequest request) {
        Task taskDetails = mapToEntity(request);
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(ApiResponse.success("Task updated successfully", mapToDTO(updatedTask)));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        Task task = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Task status updated successfully", mapToDTO(task)));
    }
    
    @PutMapping("/{id}/deliverable")
    public ResponseEntity<ApiResponse<TaskDTO>> addDeliverable(
            @PathVariable Long id,
            @RequestParam String deliverableUrl) {
        Task task = taskService.addDeliverable(id, deliverableUrl);
        return ResponseEntity.ok(ApiResponse.success("Deliverable added successfully", mapToDTO(task)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
    }
    
    @GetMapping("/count/status/{status}")
    public ResponseEntity<ApiResponse<Long>> countTasksByStatus(@PathVariable TaskStatus status) {
        Long count = taskService.countTasksByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(count));
    }
    
    private TaskDTO mapToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setType(task.getType());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setDesignBrief(task.getDesignBrief());
        dto.setReferenceImages(task.getReferenceImages());
        dto.setMoodBoard(task.getMoodBoard());
        dto.setShootDate(task.getShootDate());
        dto.setDeliverableUrls(task.getDeliverableUrls());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setSubmittedAt(task.getSubmittedAt());
        dto.setCompletedAt(task.getCompletedAt());
        
        if (task.getClient() != null) {
            dto.setClientId(task.getClient().getId());
            dto.setClientName(task.getClient().getName());
        }
        
        if (task.getAssignedTo() != null) {
            dto.setAssignedToId(task.getAssignedTo().getId());
            dto.setAssignedToName(task.getAssignedTo().getFullname());
        }
        
        if (task.getAssignedBy() != null) {
            dto.setAssignedById(task.getAssignedBy().getId());
            dto.setAssignedByName(task.getAssignedBy().getFullname());
        }
        
        return dto;
    }
    
    private Task mapToEntity(TaskCreateRequest request) {
        Task task = new Task();
        task.setType(request.getType());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setDesignBrief(request.getDesignBrief());
        task.setReferenceImages(request.getReferenceImages());
        task.setMoodBoard(request.getMoodBoard());
        task.setShootDate(request.getShootDate());
        return task;
    }
}
