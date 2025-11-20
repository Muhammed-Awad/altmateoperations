package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.AuditAction;
import com.alt_mate.altmate.model.AuditLog;
import com.alt_mate.altmate.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {
    
    private final AuditLogService auditLogService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AuditLog>> createAuditLog(@RequestBody AuditLog auditLog) {
        AuditLog createdLog = auditLogService.createAuditLog(auditLog);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Audit log created successfully", createdLog));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuditLog>> getAuditLogById(@PathVariable Long id) {
        AuditLog auditLog = auditLogService.getAuditLogById(id);
        return ResponseEntity.ok(ApiResponse.success(auditLog));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(ApiResponse.success(auditLogs));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByUser(@PathVariable Long userId) {
        List<AuditLog> auditLogs = auditLogService.getLogsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(auditLogs));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        List<AuditLog> auditLogs = auditLogService.getLogsByEntity(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success(auditLogs));
    }
    
    @GetMapping("/action/{action}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByAction(@PathVariable AuditAction action) {
        List<AuditLog> auditLogs = auditLogService.getLogsByAction(action);
        return ResponseEntity.ok(ApiResponse.success(auditLogs));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AuditLog> auditLogs = auditLogService.getLogsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(auditLogs));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAuditLog(@PathVariable Long id) {
        auditLogService.deleteAuditLog(id);
        return ResponseEntity.ok(ApiResponse.success("Audit log deleted successfully", null));
    }
}
