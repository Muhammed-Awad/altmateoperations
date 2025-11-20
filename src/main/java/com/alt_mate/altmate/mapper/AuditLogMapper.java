package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.AuditLogDTO;
import com.example.altmate_operations.model.AuditLog;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditLogMapper {
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullname", target = "userName")
    AuditLogDTO toDTO(AuditLog auditLog);
    
    List<AuditLogDTO> toDTOList(List<AuditLog> auditLogs);
}
