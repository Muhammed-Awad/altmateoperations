package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.AccountingEntryCreateRequest;
import com.alt_mate.altmate.DTO.AccountingEntryDTO;
import com.alt_mate.altmate.model.AccountingEntry;
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
