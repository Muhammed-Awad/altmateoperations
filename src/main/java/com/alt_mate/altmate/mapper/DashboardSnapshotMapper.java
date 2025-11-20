package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.DashboardSnapshotDTO;
import com.alt_mate.altmate.model.DashboardSnapshot;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DashboardSnapshotMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    DashboardSnapshotDTO toDTO(DashboardSnapshot snapshot);
    
    List<DashboardSnapshotDTO> toDTOList(List<DashboardSnapshot> snapshots);
}
