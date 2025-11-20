package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.ChatbotResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatbotResponseRepository extends JpaRepository<ChatbotResponse, Long> {
    
    List<ChatbotResponse> findByChatbotConfigId(Long chatbotConfigId);
    
    @Query("SELECT cr FROM ChatbotResponse cr WHERE cr.chatbotConfig.id = :configId AND cr.triggerKeyword = :keyword")
    Optional<ChatbotResponse> findByConfigIdAndKeyword(@Param("configId") Long configId, 
                                                        @Param("keyword") String keyword);
    
    @Query("SELECT cr FROM ChatbotResponse cr WHERE cr.chatbotConfig.id = :configId AND LOWER(cr.triggerKeyword) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ChatbotResponse> searchByKeyword(@Param("configId") Long configId, 
                                          @Param("keyword") String keyword);
}
