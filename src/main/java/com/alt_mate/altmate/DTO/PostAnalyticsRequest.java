package com.example.altmate_operations.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAnalyticsRequest {
    
    @Min(value = 0, message = "Reach must be non-negative")
    private Integer reach;
    
    @Min(value = 0, message = "Impressions must be non-negative")
    private Integer impressions;
    
    @Min(value = 0, message = "Likes must be non-negative")
    private Integer likes;
    
    @Min(value = 0, message = "Comments must be non-negative")
    private Integer comments;
    
    @Min(value = 0, message = "Shares must be non-negative")
    private Integer shares;
}
