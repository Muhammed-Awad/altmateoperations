package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.PermissionDTO;
import com.alt_mate.altmate.model.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    
    PermissionDTO toDTO(Permission permission);
    
    List<PermissionDTO> toDTOList(List<Permission> permissions);
}
