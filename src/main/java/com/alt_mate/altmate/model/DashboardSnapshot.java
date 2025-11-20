package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "dashboard_snapshots")
public class DashboardSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDate snapshotDate;

    // Content metrics
    private Integer totalPosts;
    private Integer totalReach;
    private Double avgEngagementRate;
    private Integer totalEngagement;
    private Integer totalFollowers;

    // Moderation metrics
    private Integer totalMessages;
    private Integer totalComments;
    private Long avgResponseTimeSeconds;
    private Integer activeComments;

    // Task metrics
    private Integer designTasksCompleted;
    private Integer mediaTasksCompleted;
    private Double avgTaskCompletionTime;
    private Integer activeTasks;

    // Ad metrics
    private BigDecimal totalAdSpend;
    private Integer totalImpressions;
    private Integer totalClicks;
    
    // Financial metrics
    private BigDecimal revenueGenerated;

    @CreatedDate
    private LocalDateTime createdAt;
}
