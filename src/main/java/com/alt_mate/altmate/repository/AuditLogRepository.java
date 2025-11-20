package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.AuditLog;
import com.example.altmate_operations.model.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByPerformedById(Long userId);
    
    List<AuditLog> findByAction(AuditAction action);
    
    List<AuditLog> findByEntityType(String entityType);
    
    List<AuditLog> findByEntityIdAndEntityType(Long entityId, String entityType);
    
    @Query("SELECT al FROM AuditLog al WHERE al.timestamp BETWEEN :startDate AND :endDate")
    List<AuditLog> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT al FROM AuditLog al WHERE al.performedBy.id = :userId AND al.timestamp BETWEEN :startDate AND :endDate")
    List<AuditLog> findByUserAndDateRange(@Param("userId") Long userId, 
                                         @Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT al FROM AuditLog al WHERE al.action = :action AND al.timestamp BETWEEN :startDate AND :endDate")
    List<AuditLog> findByActionAndDateRange(@Param("action") AuditAction action, 
                                           @Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT al FROM AuditLog al ORDER BY al.timestamp DESC")
    List<AuditLog> findAllOrderByTimestampDesc();
    
    @Query("SELECT COUNT(al) FROM AuditLog al WHERE al.performedBy.id = :userId")
    Long countActionsByUser(@Param("userId") Long userId);
}
