package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "chatbot_responses")
public class ChatbotResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatbot_config_id", nullable = false)
    private ChatbotConfig chatbotConfig;

    @Column(nullable = false)
    private String trigger; // Keyword or question pattern

    @Column(nullable = false, length = 2000)
    private String response;

    private Integer priority = 0; // For matching order
}