package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.Task;
import com.example.altmate_operations.model.TaskPriority;
import com.example.altmate_operations.model.TaskStatus;
import com.example.altmate_operations.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByClientId(Long clientId);
    
    List<Task> findByStatus(TaskStatus status);
    
    List<Task> findByType(TaskType type);
    
    List<Task> findByPriority(TaskPriority priority);
    
    List<Task> findByAssignedToId(Long userId);
    
    List<Task> findByAssignedById(Long userId);
    
    List<Task> findByAssignedToIdAndStatus(Long userId, TaskStatus status);
    
    List<Task> findByClientIdAndStatus(Long clientId, TaskStatus status);
    
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findByDueDateBetween(@Param("startDate") LocalDateTime startDate, 
                                    @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Task t WHERE t.dueDate < :date AND t.status NOT IN :completedStatuses")
    List<Task> findOverdueTasks(@Param("date") LocalDateTime date, 
                                @Param("completedStatuses") List<TaskStatus> completedStatuses);
    
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status = :status ORDER BY t.priority DESC, t.dueDate ASC")
    List<Task> findByAssignedToIdAndStatusOrderByPriorityAndDueDate(@Param("userId") Long userId, 
                                                                      @Param("status") TaskStatus status);
    
    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = :status")
    Long countByStatus(@Param("status") TaskStatus status);
    
    @Query("SELECT t FROM Task t WHERE t.client.id = :clientId AND t.type = :type")
    List<Task> findByClientIdAndType(@Param("clientId") Long clientId, @Param("type") TaskType type);
}
