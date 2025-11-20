package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialPlatform platform;

    @Column(nullable = false)
    private String accountUsername;

    private String accountId; // Platform's unique ID

    // OAuth tokens (MUST BE ENCRYPTED)
    @Column(length = 1000)
    private String accessToken;

    @Column(length = 1000)
    private String refreshToken;

    private LocalDateTime tokenExpiresAt;
    
    private LocalDateTime tokenExpiryDate; // Alternative field name
    
    private String accountName; // Display name

    @Column(nullable = false)
    private Boolean isConnected = true;
    
    private Boolean isActive = true; // Alternative field for active status

    @CreatedDate
    private LocalDateTime connectedAt;

    private LocalDateTime lastSyncedAt;

    // Relationships
    @OneToMany(mappedBy = "socialAccount")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "socialAccount")
    private List<InboxMessage> inboxMessages = new ArrayList<>();

    @OneToMany(mappedBy = "socialAccount")
    private List<Comment> comments = new ArrayList<>();


}
