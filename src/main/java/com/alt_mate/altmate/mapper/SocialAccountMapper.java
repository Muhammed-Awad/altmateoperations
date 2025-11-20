package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.SocialAccountDTO;
import com.example.altmate_operations.model.SocialAccount;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SocialAccountMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    SocialAccountDTO toDTO(SocialAccount account);
    
    List<SocialAccountDTO> toDTOList(List<SocialAccount> accounts);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "connectedAt", ignore = true)
    SocialAccount toEntity(SocialAccountDTO dto);
}
