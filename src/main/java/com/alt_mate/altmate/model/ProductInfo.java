package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatbot_config_id", nullable = false)
    private ChatbotConfig chatbotConfig;

    @Column(nullable = false)
    private String productName;

    @Column(length = 1000)
    private String description;

    private BigDecimal price;

    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();
}