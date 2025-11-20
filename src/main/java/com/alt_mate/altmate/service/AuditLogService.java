package com.example.altmate_operations.service;

import com.example.altmate_operations.model.AuditAction;
import com.example.altmate_operations.model.AuditLog;
import com.example.altmate_operations.model.User;
import com.example.altmate_operations.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    
    private final AuditLogRepository auditLogRepository;
    
    @Transactional
    public AuditLog logAction(User user, AuditAction action, String entityType, Long entityId, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setDetails(details);
        auditLog.setCreatedAt(LocalDateTime.now());
        auditLog.setIpAddress(getCurrentUserIp()); // Implement IP retrieval
        return auditLogRepository.save(auditLog);
    }
    
    @Transactional
    public AuditLog createAuditLog(AuditLog auditLog) {
        auditLog.setCreatedAt(LocalDateTime.now());
        return auditLogRepository.save(auditLog);
    }
    
    public List<AuditLog> getLogsByUser(Long userId) {
        return getAuditLogsByUser(userId);
    }
    
    public List<AuditLog> getLogsByEntity(String entityType, Long entityId) {
        return auditLogRepository.findByEntityIdAndEntityType(entityId, entityType);
    }
    
    public List<AuditLog> getLogsByAction(AuditAction action) {
        return getAuditLogsByAction(action);
    }
    
    public List<AuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return getAuditLogsByDateRange(startDate, endDate);
    }
    
    @Transactional
    public void deleteAuditLog(Long logId) {
        AuditLog auditLog = getAuditLogById(logId);
        auditLogRepository.delete(auditLog);
    }
    
    @Transactional
    public AuditLog logCreate(User user, String entityType, Long entityId, String details) {
        return logAction(user, AuditAction.CREATE, entityType, entityId, details);
    }
    
    @Transactional
    public AuditLog logUpdate(User user, String entityType, Long entityId, String details) {
        return logAction(user, AuditAction.UPDATE, entityType, entityId, details);
    }
    
    @Transactional
    public AuditLog logDelete(User user, String entityType, Long entityId, String details) {
        return logAction(user, AuditAction.DELETE, entityType, entityId, details);
    }
    
    @Transactional
    public AuditLog logLogin(User user, String details) {
        return logAction(user, AuditAction.LOGIN, "User", user.getId(), details);
    }
    
    @Transactional
    public AuditLog logLogout(User user, String details) {
        return logAction(user, AuditAction.LOGOUT, "User", user.getId(), details);
    }
    
    public AuditLog getAuditLogById(Long logId) {
        return auditLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Audit log not found with id: " + logId));
    }
    
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAllOrderByTimestampDesc();
    }
    
    public List<AuditLog> getAuditLogsByUser(Long userId) {
        return auditLogRepository.findByPerformedById(userId);
    }
    
    public List<AuditLog> getAuditLogsByAction(AuditAction action) {
        return auditLogRepository.findByAction(action);
    }
    
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }
    
    public List<AuditLog> getAuditLogsByEntity(Long entityId, String entityType) {
        return auditLogRepository.findByEntityIdAndEntityType(entityId, entityType);
    }
    
    public List<AuditLog> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByTimestampBetween(startDate, endDate);
    }
    
    public List<AuditLog> getAuditLogsByUserAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByUserAndDateRange(userId, startDate, endDate);
    }
    
    public List<AuditLog> getAuditLogsByActionAndDateRange(AuditAction action, LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByActionAndDateRange(action, startDate, endDate);
    }
    
    public Long countActionsByUser(Long userId) {
        return auditLogRepository.countActionsByUser(userId);
    }
    
    private String getCurrentUserIp() {
        // Implement IP address retrieval from HTTP request
        // This would typically be injected via HttpServletRequest
        return "0.0.0.0";
    }
}
