package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "client_complaints")
public class ClientComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status; // PENDING, IN_PROGRESS, RESOLVED
    
    @Column(length = 2000)
    private String resolution;

    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo; // Sent to management

    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime submittedAt;

    private LocalDateTime resolvedAt;
}
