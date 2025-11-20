package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "content_generation_requests")
public class ContentGenerationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Input files
    private String strategyFileUrl;     // Strategic file
    private String contentPlanFileUrl;  // Content plan

    @ElementCollection
    private List<String> competitorUrls = new ArrayList<>(); // 5 competitor pages

    private String mediaLibraryUrl; // Link to images/reels

    @Enumerated(EnumType.STRING)
    private GenerationStatus status; // PENDING, PROCESSING, COMPLETED, FAILED

    // Output
    private String outputFileUrl; // PowerPoint calendar

    @ManyToOne
    @JoinColumn(name = "requested_by_id")
    private User requestedBy;

    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime requestedAt;
    
    private LocalDateTime generatedAt;

    private LocalDateTime completedAt;
    
    @Column(length = 500)
    private String contentType;
    
    @Column(length = 2000)
    private String prompt;
    
    @Column(length = 5000)
    private String generatedContent;

    @Column(length = 1000)
    private String errorMessage; // If failed
}