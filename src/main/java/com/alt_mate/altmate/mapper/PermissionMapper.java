package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.PermissionDTO;
import com.example.altmate_operations.model.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    
    PermissionDTO toDTO(Permission permission);
    
    List<PermissionDTO> toDTOList(List<Permission> permissions);
}
