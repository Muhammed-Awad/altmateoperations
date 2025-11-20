package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.InboxMessageCreateRequest;
import com.example.altmate_operations.DTO.InboxMessageDTO;
import com.example.altmate_operations.model.InboxMessage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InboxMessageMapper {
    
    @Mapping(source = "socialAccount.client.id", target = "clientId")
    @Mapping(source = "socialAccount.client.name", target = "clientName")
    @Mapping(source = "socialAccount.id", target = "socialAccountId")
    @Mapping(source = "socialAccount.accountUsername", target = "socialAccountUsername")
    @Mapping(source = "socialAccount.platform", target = "platform")
    InboxMessageDTO toDTO(InboxMessage message);
    
    List<InboxMessageDTO> toDTOList(List<InboxMessage> messages);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "socialAccount", ignore = true)
    @Mapping(target = "receivedAt", ignore = true)
    @Mapping(target = "respondedAt", ignore = true)
    @Mapping(target = "status", constant = "UNREAD")
    InboxMessage toEntity(InboxMessageCreateRequest request);
}
