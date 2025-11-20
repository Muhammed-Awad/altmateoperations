package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.CampaignObjective;
import com.example.altmate_operations.model.CampaignStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCampaignCreateRequest {
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Social account ID is required")
    private Long socialAccountId;
    
    @NotBlank(message = "Campaign name is required")
    @Size(min = 3, max = 100, message = "Campaign name must be between 3 and 100 characters")
    private String campaignName;
    
    @NotNull(message = "Campaign objective is required")
    private CampaignObjective objective;
    
    @NotNull(message = "Total budget is required")
    @DecimalMin(value = "0.01", message = "Budget must be greater than zero")
    private BigDecimal totalBudget;
    
    private Boolean autoPauseEnabled = false;
    
    @NotNull(message = "Campaign status is required")
    private CampaignStatus status;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Long managedById;
}
