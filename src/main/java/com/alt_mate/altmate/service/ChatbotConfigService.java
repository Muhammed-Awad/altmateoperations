package com.example.altmate_operations.service;

import com.example.altmate_operations.exception.BadRequestException;
import com.example.altmate_operations.model.ChatbotConfig;
import com.example.altmate_operations.model.ChatbotType;
import com.example.altmate_operations.repository.ChatbotConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatbotConfigService {
    
    private final ChatbotConfigRepository chatbotConfigRepository;
    
    @Transactional
    public ChatbotConfig createChatbotConfig(ChatbotConfig config) {
        if (chatbotConfigRepository.existsByClientId(config.getClient().getId())) {
            throw new BadRequestException("Chatbot config already exists for this client");
        }
        config.setCreatedAt(LocalDateTime.now());
        config.setUpdatedAt(LocalDateTime.now());
        return chatbotConfigRepository.save(config);
    }
    
    @Transactional
    public ChatbotConfig updateChatbotConfig(Long configId, ChatbotConfig configDetails) {
        ChatbotConfig config = getChatbotConfigById(configId);
        config.setType(configDetails.getType());
        config.setBusinessDescription(configDetails.getBusinessDescription());
        config.setResponseStyle(configDetails.getResponseStyle());
        config.setIsActive(configDetails.getIsActive());
        config.setUpdatedAt(LocalDateTime.now());
        return chatbotConfigRepository.save(config);
    }
    
    @Transactional
    public ChatbotConfig activateChatbot(Long configId) {
        ChatbotConfig config = getChatbotConfigById(configId);
        config.setIsActive(true);
        config.setUpdatedAt(LocalDateTime.now());
        return chatbotConfigRepository.save(config);
    }
    
    @Transactional
    public ChatbotConfig deactivateChatbot(Long configId) {
        ChatbotConfig config = getChatbotConfigById(configId);
        config.setIsActive(false);
        config.setUpdatedAt(LocalDateTime.now());
        return chatbotConfigRepository.save(config);
    }
    
    @Transactional
    public void deleteChatbotConfig(Long configId) {
        ChatbotConfig config = getChatbotConfigById(configId);
        chatbotConfigRepository.delete(config);
    }
    
    public ChatbotConfig getChatbotConfigById(Long configId) {
        return chatbotConfigRepository.findById(configId)
                .orElseThrow(() -> new RuntimeException("Chatbot config not found with id: " + configId));
    }
    
    public Optional<ChatbotConfig> getChatbotConfigByClient(Long clientId) {
        return chatbotConfigRepository.findByClientId(clientId);
    }
    
    public List<ChatbotConfig> getAllChatbotConfigs() {
        return chatbotConfigRepository.findAll();
    }
    
    public List<ChatbotConfig> getConfigsByClient(Long clientId) {
        return chatbotConfigRepository.findByClientId(clientId)
                .map(List::of)
                .orElse(List.of());
    }
    
    public List<ChatbotConfig> getConfigsByType(ChatbotType type) {
        return getChatbotConfigsByType(type);
    }
    
    public List<ChatbotConfig> getActiveConfigs() {
        return chatbotConfigRepository.findByIsActive(true);
    }
    
    public List<ChatbotConfig> getChatbotConfigsByType(ChatbotType type) {
        return chatbotConfigRepository.findByType(type);
    }
    
    public List<ChatbotConfig> getActiveChatbotConfigs() {
        return chatbotConfigRepository.findByIsActive(true);
    }
    
    public List<ChatbotConfig> getChatbotConfigsByTypeAndStatus(ChatbotType type, Boolean isActive) {
        return chatbotConfigRepository.findByTypeAndIsActive(type, isActive);
    }
    
    public List<ChatbotConfig> getChatbotConfigsByConfigurator(Long userId) {
        return chatbotConfigRepository.findByConfiguredById(userId);
    }
    
    public Long countActiveChatbots() {
        return chatbotConfigRepository.countActiveChatbots();
    }
    
    public boolean existsByClientId(Long clientId) {
        return chatbotConfigRepository.existsByClientId(clientId);
    }
}
