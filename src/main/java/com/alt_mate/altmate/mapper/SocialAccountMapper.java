package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.SocialAccountDTO;
import com.alt_mate.altmate.model.SocialAccount;
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
