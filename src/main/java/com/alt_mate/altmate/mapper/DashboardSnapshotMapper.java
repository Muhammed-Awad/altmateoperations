package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.DashboardSnapshotDTO;
import com.example.altmate_operations.model.DashboardSnapshot;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DashboardSnapshotMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    DashboardSnapshotDTO toDTO(DashboardSnapshot snapshot);
    
    List<DashboardSnapshotDTO> toDTOList(List<DashboardSnapshot> snapshots);
}
