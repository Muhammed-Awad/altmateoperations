package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.DTO.ChatbotConfigDTO;
import com.alt_mate.altmate.model.ChatbotConfig;
import com.alt_mate.altmate.model.ChatbotType;
import com.alt_mate.altmate.service.ChatbotConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chatbot-configs")
@RequiredArgsConstructor
public class ChatbotConfigController {
    
    private final ChatbotConfigService chatbotConfigService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ChatbotConfigDTO>> createConfig(@RequestBody ChatbotConfig config) {
        ChatbotConfig createdConfig = chatbotConfigService.createChatbotConfig(config);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Chatbot config created successfully", mapToDTO(createdConfig)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChatbotConfigDTO>> getConfigById(@PathVariable Long id) {
        ChatbotConfig config = chatbotConfigService.getChatbotConfigById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(config)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatbotConfigDTO>>> getAllConfigs() {
        List<ChatbotConfig> configs = chatbotConfigService.getAllChatbotConfigs();
        List<ChatbotConfigDTO> configDTOs = configs.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(configDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<ChatbotConfigDTO>>> getConfigsByClient(@PathVariable Long clientId) {
        List<ChatbotConfig> configs = chatbotConfigService.getConfigsByClient(clientId);
        List<ChatbotConfigDTO> configDTOs = configs.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(configDTOs));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<ChatbotConfigDTO>>> getConfigsByType(@PathVariable ChatbotType type) {
        List<ChatbotConfig> configs = chatbotConfigService.getConfigsByType(type);
        List<ChatbotConfigDTO> configDTOs = configs.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(configDTOs));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ChatbotConfigDTO>>> getActiveConfigs() {
        List<ChatbotConfig> configs = chatbotConfigService.getActiveConfigs();
        List<ChatbotConfigDTO> configDTOs = configs.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(configDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ChatbotConfigDTO>> updateConfig(
            @PathVariable Long id,
            @RequestBody ChatbotConfig configDetails) {
        ChatbotConfig updatedConfig = chatbotConfigService.updateChatbotConfig(id, configDetails);
        return ResponseEntity.ok(ApiResponse.success("Chatbot config updated successfully", mapToDTO(updatedConfig)));
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<ChatbotConfigDTO>> activateConfig(@PathVariable Long id) {
        ChatbotConfig config = chatbotConfigService.activateChatbot(id);
        return ResponseEntity.ok(ApiResponse.success("Chatbot activated successfully", mapToDTO(config)));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ChatbotConfigDTO>> deactivateConfig(@PathVariable Long id) {
        ChatbotConfig config = chatbotConfigService.deactivateChatbot(id);
        return ResponseEntity.ok(ApiResponse.success("Chatbot deactivated successfully", mapToDTO(config)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteConfig(@PathVariable Long id) {
        chatbotConfigService.deleteChatbotConfig(id);
        return ResponseEntity.ok(ApiResponse.success("Chatbot config deleted successfully", null));
    }
    
    private ChatbotConfigDTO mapToDTO(ChatbotConfig config) {
        ChatbotConfigDTO dto = new ChatbotConfigDTO();
        dto.setId(config.getId());
        dto.setType(config.getType());
        dto.setWelcomeMessage(config.getWelcomeMessage());
        dto.setFallbackMessage(config.getFallbackMessage());
        dto.setIsActive(config.getIsActive());
        dto.setCreatedAt(config.getCreatedAt());
        dto.setUpdatedAt(config.getUpdatedAt());
        
        if (config.getClient() != null) {
            dto.setClientId(config.getClient().getId());
            dto.setClientName(config.getClient().getName());
        }
        
        return dto;
    }
}
