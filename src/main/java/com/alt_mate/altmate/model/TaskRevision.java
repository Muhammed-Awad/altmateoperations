package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task_revisions")
public class TaskRevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(length = 2000)
    private String revisionNotes;

    @ManyToOne
    @JoinColumn(name = "requested_by_id")
    private User requestedBy;

    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime requestedAt;
    
    private Integer revisionNumber;
    
    @Column(length = 2000)
    private String feedback;
    
    private LocalDateTime completedAt;

}
