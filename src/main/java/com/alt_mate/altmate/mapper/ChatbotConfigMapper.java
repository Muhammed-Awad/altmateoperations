package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.ChatbotConfigDTO;
import com.alt_mate.altmate.model.ChatbotConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatbotConfigMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    ChatbotConfigDTO toDTO(ChatbotConfig config);
    
    List<ChatbotConfigDTO> toDTOList(List<ChatbotConfig> configs);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ChatbotConfig toEntity(ChatbotConfigDTO dto);
}
