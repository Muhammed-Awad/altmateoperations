package com.alt_mate.altmate.DTO;

import com.alt_mate.altmate.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long socialAccountId;
    private String socialAccountName;
    private String content;
    private String hook;
    private String slogan;
    private List<String> mediaUrls;
    private PostStatus status;
    private LocalDateTime scheduledAt;
    private LocalDateTime publishedAt;
    private Integer reach;
    private Integer impressions;
    private Integer likes;
    private Integer comments;
    private Integer shares;
    private Double engagementRate;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
