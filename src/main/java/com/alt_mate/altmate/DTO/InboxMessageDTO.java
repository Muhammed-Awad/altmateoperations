package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.MessageStatus;
import com.alt_mate.altmate.model.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboxMessageDTO {
    
    private Long id;
    private String senderName;
    private String senderProfileUrl;
    private String messageText;
    private MessageStatus status;
    private String responseText;
    private LocalDateTime receivedAt;
    private LocalDateTime respondedAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
    
    // Social Account relationship
    private Long socialAccountId;
    private String socialAccountUsername;
    private SocialPlatform platform;
}
