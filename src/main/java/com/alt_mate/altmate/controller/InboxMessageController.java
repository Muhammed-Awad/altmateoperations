package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.DTO.InboxMessageCreateRequest;
import com.alt_mate.altmate.DTO.InboxMessageDTO;
import com.alt_mate.altmate.mapper.InboxMessageMapper;
import com.alt_mate.altmate.model.InboxMessage;
import com.alt_mate.altmate.model.MessageStatus;
import com.alt_mate.altmate.service.InboxMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inbox")
@RequiredArgsConstructor
public class InboxMessageController {
    
    private final InboxMessageService inboxMessageService;
    private final InboxMessageMapper inboxMessageMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<InboxMessageDTO>> createMessage(@Valid @RequestBody InboxMessageCreateRequest request) {
        InboxMessage message = inboxMessageMapper.toEntity(request);
        InboxMessage createdMessage = inboxMessageService.createMessage(message);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(createdMessage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Message created successfully", dto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InboxMessageDTO>> getMessageById(@PathVariable Long id) {
        InboxMessage message = inboxMessageService.getMessageById(id);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(message);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<InboxMessageDTO>>> getAllMessages() {
        List<InboxMessage> messages = inboxMessageService.getAllMessages();
        List<InboxMessageDTO> dtos = inboxMessageMapper.toDTOList(messages);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<InboxMessageDTO>>> getMessagesByClient(@PathVariable Long clientId) {
        List<InboxMessage> messages = inboxMessageService.getMessagesByClient(clientId);
        List<InboxMessageDTO> dtos = inboxMessageMapper.toDTOList(messages);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<InboxMessageDTO>>> getMessagesByStatus(@PathVariable MessageStatus status) {
        List<InboxMessage> messages = inboxMessageService.getMessagesByStatus(status);
        List<InboxMessageDTO> dtos = inboxMessageMapper.toDTOList(messages);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<InboxMessageDTO>>> getMessagesBySocialAccount(@PathVariable Long accountId) {
        List<InboxMessage> messages = inboxMessageService.getMessagesBySocialAccount(accountId);
        List<InboxMessageDTO> dtos = inboxMessageMapper.toDTOList(messages);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<List<InboxMessageDTO>>> getUnreadMessages() {
        List<InboxMessage> messages = inboxMessageService.getUnreadMessages();
        List<InboxMessageDTO> dtos = inboxMessageMapper.toDTOList(messages);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InboxMessageDTO>> updateMessage(
            @PathVariable Long id,
            @RequestBody InboxMessage messageDetails) {
        InboxMessage updatedMessage = inboxMessageService.updateMessage(id, messageDetails);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(updatedMessage);
        return ResponseEntity.ok(ApiResponse.success("Message updated successfully", dto));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<InboxMessageDTO>> updateMessageStatus(
            @PathVariable Long id,
            @RequestParam MessageStatus status) {
        InboxMessage message = inboxMessageService.updateMessageStatus(id, status);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(message);
        return ResponseEntity.ok(ApiResponse.success("Message status updated successfully", dto));
    }
    
    @PutMapping("/{id}/mark-read")
    public ResponseEntity<ApiResponse<InboxMessageDTO>> markAsRead(@PathVariable Long id) {
        InboxMessage message = inboxMessageService.markAsRead(id);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(message);
        return ResponseEntity.ok(ApiResponse.success("Message marked as read", dto));
    }
    
    @PutMapping("/{id}/respond")
    public ResponseEntity<ApiResponse<InboxMessageDTO>> respondToMessage(
            @PathVariable Long id,
            @RequestParam String response) {
        InboxMessage message = inboxMessageService.respondToMessage(id, response);
        InboxMessageDTO dto = inboxMessageMapper.toDTO(message);
        return ResponseEntity.ok(ApiResponse.success("Response sent successfully", dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMessage(@PathVariable Long id) {
        inboxMessageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }
    
    @GetMapping("/count/unread")
    public ResponseEntity<ApiResponse<Long>> countUnreadMessages() {
        Long count = inboxMessageService.countUnreadMessages();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
