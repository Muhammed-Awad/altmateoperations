package com.alt_mate.altmate.model;

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
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type; // DESIGN, MEDIA_SHOOT, CONTENT_WRITING

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status; // PENDING, IN_PROGRESS, SUBMITTED, etc.

    @Enumerated(EnumType.STRING)
    private TaskPriority priority; // LOW, MEDIUM, HIGH, URGENT

    private LocalDateTime dueDate;

    // Assignment
    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo; // Designer, Media, etc.

    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    private User assignedBy; // Content creator

    // Design-specific fields
    private String designBrief;

    @ElementCollection
    private List<String> referenceImages = new ArrayList<>();

    // Media-specific fields
    private String moodBoard;
    private LocalDateTime shootDate;

    // Deliverables
    @ElementCollection
    private List<String> deliverableUrls = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime submittedAt;
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskRevision> revisions = new ArrayList<>();
}
