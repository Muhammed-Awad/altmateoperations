package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccountDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private SocialPlatform platform;
    private String accountUsername;
    private String accountId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime tokenExpiry;
    private Boolean isConnected;
    private Boolean isActive;
    private LocalDateTime connectedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastSyncedAt;
}
