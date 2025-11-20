package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.ContentGenerationRequestDTO;
import com.alt_mate.altmate.model.ContentGenerationRequest;
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
