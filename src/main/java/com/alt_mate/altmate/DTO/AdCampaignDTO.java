package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.CampaignObjective;
import com.example.altmate_operations.model.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCampaignDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long socialAccountId;
    private String socialAccountName;
    private String campaignName;
    private CampaignObjective objective;
    private BigDecimal totalBudget;
    private BigDecimal spentAmount;
    private BigDecimal remainingBudget;
    private Boolean autoPauseEnabled;
    private CampaignStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer impressions;
    private Integer clicks;
    private Integer conversions;
    private BigDecimal costPerClick;
    private BigDecimal costPerConversion;
    private Long managedById;
    private String managedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
