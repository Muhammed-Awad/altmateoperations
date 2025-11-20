package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AuditAction action; // LOGIN, CREATE_POST, DELETE_CLIENT, etc.

    private String entityType; // "Post", "Client", "Task"
    private Long entityId;

    @Column(length = 2000)
    private String details; // JSON with before/after values

    private String ipAddress;

    @CreatedDate
    private LocalDateTime createdAt;
}