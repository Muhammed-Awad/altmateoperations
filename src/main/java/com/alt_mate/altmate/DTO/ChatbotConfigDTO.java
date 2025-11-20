package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.ChatbotType;
import com.example.altmate_operations.model.ResponseStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotConfigDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private ChatbotType type;
    private Boolean isActive;
    private String businessDescription;
    private ResponseStyle responseStyle;
    private String welcomeMessage;
    private String fallbackMessage;
    private Long configuredById;
    private String configuredByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
