package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataDTO {
    
    private Long id;
    private String fileName;
    private FileType fileType;
    private Long fileSize;
    private String fileUrl;
    private String mimeType;
    private LocalDateTime uploadedAt;
    
    // Client relationship
    private Long clientId;
    private String clientName;
    
    // Uploaded by relationship
    private Long uploadedById;
    private String uploadedByName;
}
