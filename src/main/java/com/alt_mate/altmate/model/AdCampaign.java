package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_campaigns")
public class AdCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccount socialAccount;

    @Column(nullable = false)
    private String campaignName;

    @Enumerated(EnumType.STRING)
    private CampaignObjective objective; // AWARENESS, TRAFFIC, CONVERSIONS, etc.

    // Budget management
    @Column(nullable = false)
    private BigDecimal totalBudget;

    @Column(nullable = false)
    private BigDecimal spentAmount = BigDecimal.ZERO;

    private BigDecimal remainingBudget;

    // Auto-pause when budget exhausted
    private Boolean autoPauseEnabled = false;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status; // ACTIVE, PAUSED, COMPLETED

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Campaign results
    private Integer impressions = 0;
    private Integer clicks = 0;
    private Integer conversions = 0;
    private BigDecimal costPerClick = BigDecimal.ZERO;
    private BigDecimal costPerConversion = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "managed_by_id")
    private User managedBy; // Media buyer

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
