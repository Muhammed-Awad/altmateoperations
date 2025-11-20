package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    
    private Long id;
    private String commenterName;
    private String commenterProfileUrl;
    private String commentText;
    private CommentStatus status;
    private String responseText;
    private LocalDateTime commentedAt;
    private LocalDateTime respondedAt;
    
    // Post relationship
    private Long postId;
    private String postContent;
}
