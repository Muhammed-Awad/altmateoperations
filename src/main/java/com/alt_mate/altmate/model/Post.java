package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccount socialAccount;

    @Column(length = 5000)
    private String content;

    private String hook;

    private String slogan;

    @ElementCollection
    private List<String> mediaUrls = new ArrayList<>(); // Images/videos

    @Enumerated(EnumType.STRING)
    private PostStatus status; // DRAFT, SCHEDULED, PUBLISHED, FAILED

    private LocalDateTime scheduledAt;

    private LocalDateTime publishedAt;

    // Analytics
    private Integer reach = 0;
    private Integer impressions = 0;
    private Integer likes = 0;
    private Integer comments = 0;
    private Integer shares = 0;
    private Double engagementRate = 0.0;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
