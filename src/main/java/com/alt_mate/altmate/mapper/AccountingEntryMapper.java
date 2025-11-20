package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.AccountingEntryCreateRequest;
import com.example.altmate_operations.DTO.AccountingEntryDTO;
import com.example.altmate_operations.model.AccountingEntry;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountingEntryMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    AccountingEntryDTO toDTO(AccountingEntry entry);
    
    List<AccountingEntryDTO> toDTOList(List<AccountingEntry> entries);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    AccountingEntry toEntity(AccountingEntryCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    void updateEntityFromDTO(AccountingEntryCreateRequest request, @MappingTarget AccountingEntry entry);
}
