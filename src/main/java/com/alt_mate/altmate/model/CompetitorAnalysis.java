package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "competitor_analysis")
public class CompetitorAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_generation_request_id")
    private ContentGenerationRequest request;

    private String competitorUrl;
    private String competitorName;

    // Top performing posts
    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
    private List<TopPost> topPosts = new ArrayList<>();

    private LocalDateTime analyzedAt;
    
    @Column(length = 2000)
    private String strengths;
    
    @Column(length = 2000)
    private String weaknesses;
    
    @Column(length = 2000)
    private String contentStrategy;
    
    @Column(length = 2000)
    private String recommendations;
}