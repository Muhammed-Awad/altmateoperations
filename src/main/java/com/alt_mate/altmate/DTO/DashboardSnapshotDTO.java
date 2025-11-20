package com.example.altmate_operations.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSnapshotDTO {
    
    private Long id;
    private LocalDate snapshotDate;
    private Integer totalPosts;
    private Integer totalReach;
    private Integer totalEngagement;
    private Integer newFollowers;
    private Double engagementRate;
    private LocalDateTime createdAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
}
