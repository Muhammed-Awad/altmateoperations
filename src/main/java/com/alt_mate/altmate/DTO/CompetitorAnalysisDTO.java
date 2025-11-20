package com.example.altmate_operations.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitorAnalysisDTO {
    
    private Long id;
    private String competitorName;
    private String competitorUrl;
    private Integer followersCount;
    private Double averageEngagementRate;
    private String topPerformingContent;
    private String insights;
    private LocalDateTime analyzedAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
}
