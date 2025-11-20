package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatbot_configs")
public class ChatbotConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatbotType type; // FIXED, AI_POWERED

    @Column(nullable = false)
    private Boolean isActive = false;

    // Client business info (for AI training)
    @Column(length = 2000)
    private String businessDescription;

    @Enumerated(EnumType.STRING)
    private ResponseStyle responseStyle; // SALES, INQUIRY, ORDER_TAKING

    // Product catalog (for AI context)
    @OneToMany(mappedBy = "chatbotConfig", cascade = CascadeType.ALL)
    private List<ProductInfo> products = new ArrayList<>();

    // Fixed responses
    @OneToMany(mappedBy = "chatbotConfig", cascade = CascadeType.ALL)
    private List<ChatbotResponse> fixedResponses = new ArrayList<>();

    @Column(length = 1000)
    private String welcomeMessage;
    
    @Column(length = 1000)
    private String fallbackMessage;

    @ManyToOne
    @JoinColumn(name = "configured_by_id")
    private User configuredBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
