package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.ChatbotType;
import com.alt_mate.altmate.model.ResponseStyle;
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
