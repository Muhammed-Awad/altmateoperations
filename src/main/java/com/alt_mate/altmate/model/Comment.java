package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccount socialAccount;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String commenterName;
    private String commenterId;

    @Column(length = 2000)
    private String commentText;

    @Enumerated(EnumType.STRING)
    private CommentStatus status; // PENDING, REPLIED, HIDDEN

    private LocalDateTime commentedAt;

    @ManyToOne
    @JoinColumn(name = "replied_by_id")
    private User repliedBy;

    private LocalDateTime repliedAt;

    @Column(length = 2000)
    private String replyText;

    // Additional fields for response handling
    @Column(length = 2000)
    private String responseText;
    
    private LocalDateTime respondedAt;
}
