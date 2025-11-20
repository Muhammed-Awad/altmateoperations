package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.ContentGenerationRequestDTO;
import com.example.altmate_operations.model.ContentGenerationRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentGenerationRequestMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "requestedBy.id", target = "requestedById")
    @Mapping(source = "requestedBy.fullname", target = "requestedByName")
    ContentGenerationRequestDTO toDTO(ContentGenerationRequest request);
    
    List<ContentGenerationRequestDTO> toDTOList(List<ContentGenerationRequest> requests);
}
