package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.Task;
import com.alt_mate.altmate.model.TaskPriority;
import com.alt_mate.altmate.model.TaskStatus;
import com.alt_mate.altmate.model.TaskType;
import com.alt_mate.altmate.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    @Transactional
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task updateTask(Long taskId, Task taskDetails) {
        Task task = getTaskById(taskId);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setType(taskDetails.getType());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        task.setDesignBrief(taskDetails.getDesignBrief());
        task.setMoodBoard(taskDetails.getMoodBoard());
        task.setShootDate(taskDetails.getShootDate());
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = getTaskById(taskId);
        task.setStatus(status);
        
        if (status == TaskStatus.SUBMITTED) {
            task.setSubmittedAt(LocalDateTime.now());
        } else if (status == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }
        
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task assignTask(Long taskId, Long userId) {
        Task task = getTaskById(taskId);
        // Assuming User is fetched from UserService
        // task.setAssignedTo(user);
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task addDeliverable(Long taskId, String deliverableUrl) {
        Task task = getTaskById(taskId);
        task.getDeliverableUrls().add(deliverableUrl);
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task removeDeliverable(Long taskId, String deliverableUrl) {
        Task task = getTaskById(taskId);
        task.getDeliverableUrls().remove(deliverableUrl);
        return taskRepository.save(task);
    }
    
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = getTaskById(taskId);
        taskRepository.delete(task);
    }
    
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getTasksByClient(Long clientId) {
        return taskRepository.findByClientId(clientId);
    }
    
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    public List<Task> getTasksByType(TaskType type) {
        return taskRepository.findByType(type);
    }
    
    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }
    
    public List<Task> getTasksAssignedToUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }
    
    public List<Task> getTasksAssignedByUser(Long userId) {
        return taskRepository.findByAssignedById(userId);
    }
    
    public List<Task> getTasksByUserAndStatus(Long userId, TaskStatus status) {
        return taskRepository.findByAssignedToIdAndStatus(userId, status);
    }
    
    public List<Task> getTasksByClientAndStatus(Long clientId, TaskStatus status) {
        return taskRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<Task> getTasksByDueDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }
    
    public List<Task> getOverdueTasks() {
        List<TaskStatus> completedStatuses = Arrays.asList(TaskStatus.COMPLETED, TaskStatus.CANCELLED);
        return taskRepository.findOverdueTasks(LocalDateTime.now(), completedStatuses);
    }
    
    public List<Task> getUserTasksPrioritized(Long userId, TaskStatus status) {
        return taskRepository.findByAssignedToIdAndStatusOrderByPriorityAndDueDate(userId, status);
    }
    
    public List<Task> getTasksByClientAndType(Long clientId, TaskType type) {
        return taskRepository.findByClientIdAndType(clientId, type);
    }
    
    public Long countTasksByStatus(TaskStatus status) {
        return taskRepository.countByStatus(status);
    }
}
