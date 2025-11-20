package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.AuditLogDTO;
import com.alt_mate.altmate.model.AuditLog;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditLogMapper {
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullname", target = "userName")
    AuditLogDTO toDTO(AuditLog auditLog);
    
    List<AuditLogDTO> toDTOList(List<AuditLog> auditLogs);
}
