package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "inbox_messages")
public class InboxMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccount socialAccount;

    private String senderName;
    private String senderId; // Platform's user ID

    @Column(length = 2000)
    private String messageText;

    @Enumerated(EnumType.STRING)
    private MessageStatus status; // UNREAD, READ, REPLIED

    private LocalDateTime receivedAt;

    @ManyToOne
    @JoinColumn(name = "replied_by_id")
    private User repliedBy;

    private LocalDateTime repliedAt;

    @Column(length = 2000)
    private String replyText;
    
    @Column(length = 2000)
    private String responseText; // Alternative field for response
    
    private LocalDateTime respondedAt; // Alternative field for replied at

    // Time to first response (for analytics)
    private Long responseTimeSeconds;
}

