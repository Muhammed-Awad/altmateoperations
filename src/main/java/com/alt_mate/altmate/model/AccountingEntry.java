package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounting_entries")
public class AccountingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType type; // COLLECTION_AD, COLLECTION_PACKAGE, COLLECTION_DEBT

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PENDING, PAID, OVERDUE
    
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // Alternative field name used in some controllers

    private LocalDateTime dueDate;
    private LocalDateTime paidDate;

    @Column(length = 1000)
    private String description;
    
    @Column(length = 1000)
    private String notes;

    @Column(length = 500)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "recorded_by_id")
    private User recordedBy; // Accountant

    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime date; // Transaction/Entry date
}
