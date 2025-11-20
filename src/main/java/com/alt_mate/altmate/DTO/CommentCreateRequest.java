package com.alt_mate.altmate.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    
    @NotBlank(message = "Commenter name is required")
    private String commenterName;
    
    private String commenterProfileUrl;
    
    @NotBlank(message = "Comment text is required")
    private String commentText;
    
    @NotNull(message = "Post ID is required")
    private Long postId;
}
