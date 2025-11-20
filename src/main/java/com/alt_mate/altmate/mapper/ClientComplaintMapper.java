package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.ClientComplaintCreateRequest;
import com.example.altmate_operations.DTO.ClientComplaintDTO;
import com.example.altmate_operations.model.ClientComplaint;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientComplaintMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.fullname", target = "assignedToName")
    ClientComplaintDTO toDTO(ClientComplaint complaint);
    
    List<ClientComplaintDTO> toDTOList(List<ClientComplaint> complaints);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    ClientComplaint toEntity(ClientComplaintCreateRequest request);
}
