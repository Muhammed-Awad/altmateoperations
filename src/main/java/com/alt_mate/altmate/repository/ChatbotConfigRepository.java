package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.ChatbotConfig;
import com.example.altmate_operations.model.ChatbotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatbotConfigRepository extends JpaRepository<ChatbotConfig, Long> {
    
    Optional<ChatbotConfig> findByClientId(Long clientId);
    
    List<ChatbotConfig> findByType(ChatbotType type);
    
    List<ChatbotConfig> findByIsActive(Boolean isActive);
    
    List<ChatbotConfig> findByTypeAndIsActive(ChatbotType type, Boolean isActive);
    
    @Query("SELECT cc FROM ChatbotConfig cc WHERE cc.configuredBy.id = :userId")
    List<ChatbotConfig> findByConfiguredById(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(cc) FROM ChatbotConfig cc WHERE cc.isActive = true")
    Long countActiveChatbots();
    
    boolean existsByClientId(Long clientId);
}
