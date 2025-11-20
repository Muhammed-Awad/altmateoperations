package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Social account ID is required")
    private Long socialAccountId;
    
    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    private String content;
    
    private String hook;
    
    private String slogan;
    
    private List<String> mediaUrls;
    
    @NotNull(message = "Post status is required")
    private PostStatus status;
    
    private LocalDateTime scheduledAt;
    
    @NotNull(message = "Creator ID is required")
    private Long createdById;
}
