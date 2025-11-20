package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.ClientCreateRequest;
import com.example.altmate_operations.DTO.ClientDTO;
import com.example.altmate_operations.model.Client;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    
    ClientDTO toDTO(Client client);
    
    List<ClientDTO> toDTOList(List<Client> clients);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Client toEntity(ClientCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ClientCreateRequest request, @MappingTarget Client client);
}
