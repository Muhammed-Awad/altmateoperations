package com.alt_mate.altmate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "file_metadata")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private String fileName;

    private String originalFileName;

    @Enumerated(EnumType.STRING)
    private FileType fileType; // IMAGE, VIDEO, DOCUMENT, DESIGN

    private Long fileSize; // in bytes

    @Column(nullable = false)
    private String googleDriveFileId;

    private String googleDriveUrl;

    @ManyToOne
    @JoinColumn(name = "uploaded_by_id")
    private User uploadedBy;

    @CreatedDate
    private LocalDateTime uploadedAt;
    
    @Column(length = 1000)
    private String description;
}
