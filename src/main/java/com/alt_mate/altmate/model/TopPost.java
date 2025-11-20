package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "top_posts")
public class TopPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne

    @JoinColumn(name = "competitor_analysis_id")
    private CompetitorAnalysis analysis;

    private String postUrl;

    @Column(length = 2000)
    private String postContent;

    private Integer likes;
    private Integer comments;
    private Integer shares;
    private Integer totalEngagement;

    private LocalDateTime publishedAt;
}