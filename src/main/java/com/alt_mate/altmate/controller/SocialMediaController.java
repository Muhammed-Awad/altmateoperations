package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.SocialPlatform;
import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.service.SocialMediaIntegrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
public class SocialMediaController {
    
    private final SocialMediaIntegrationService socialMediaService;
    
    /**
     * Get OAuth authorization URL for any platform
     * Platforms: FACEBOOK, INSTAGRAM, THREADS, TWITTER, LINKEDIN, SNAPCHAT
     */
    @GetMapping("/auth-url/{platform}")
    public ResponseEntity<ApiResponse<String>> getAuthorizationUrl(
            @PathVariable String platform,
            @RequestParam Long clientId) {
        
        SocialPlatform platformEnum = SocialPlatform.valueOf(platform.toUpperCase());
        String authUrl = socialMediaService.getAuthorizationUrl(platformEnum, clientId);
        
        return ResponseEntity.ok(ApiResponse.success(
            "Authorization URL for " + platform, 
            authUrl
        ));
    }
    
    /**
     * OAuth callback handler for all platforms
     */
    @GetMapping("/callback/{platform}")
    public ResponseEntity<ApiResponse<String>> handleCallback(
            @PathVariable String platform,
            @RequestParam String code,
            @RequestParam String state) {
        
        SocialPlatform platformEnum = SocialPlatform.valueOf(platform.toUpperCase());
        Long clientId = Long.parseLong(state);
        
        SocialAccount account = socialMediaService.handleOAuthCallback(platformEnum, code, clientId);
        
        return ResponseEntity.ok(ApiResponse.success(
            platform.toUpperCase() + " account connected successfully! Account: " + account.getAccountName(),
            "You can close this window and return to the app."
        ));
    }
    
    /**
     * Publish a post to any connected social media platform
     */
    @PostMapping("/publish")
    public ResponseEntity<ApiResponse<Map<String, String>>> publishPost(
            @RequestParam Long socialAccountId,
            @RequestParam String message,
            @RequestParam(required = false) String mediaUrl) {
        
        Map<String, String> result = socialMediaService.publishPost(socialAccountId, message, mediaUrl);
        
        return ResponseEntity.ok(ApiResponse.success(
            "Post published successfully to " + result.get("platform"),
            result
        ));
    }
    
    /**
     * Get account analytics/insights
     */
    @GetMapping("/insights/{socialAccountId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getInsights(
            @PathVariable Long socialAccountId) {
        
        Map<String, Object> insights = socialMediaService.getAccountInsights(socialAccountId);
        
        return ResponseEntity.ok(ApiResponse.success(
            "Account insights retrieved successfully",
            insights
        ));
    }
    
    /**
     * Get all supported platforms
     */
    @GetMapping("/platforms")
    public ResponseEntity<ApiResponse<String[]>> getSupportedPlatforms() {
        String[] platforms = {
            "FACEBOOK", 
            "INSTAGRAM", 
            "THREADS", 
            "TWITTER", 
            "LINKEDIN", 
            "SNAPCHAT"
        };
        
        return ResponseEntity.ok(ApiResponse.success(
            "Supported social media platforms",
            platforms
        ));
    }
}
