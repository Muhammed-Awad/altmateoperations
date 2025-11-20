package com.alt_mate.altmate.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboxMessageCreateRequest {
    
    @NotBlank(message = "Sender name is required")
    private String senderName;
    
    private String senderProfileUrl;
    
    @NotBlank(message = "Message text is required")
    private String messageText;
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Social account ID is required")
    private Long socialAccountId;
}
