package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.service.FacebookService;
import com.alt_mate.altmate.service.SocialAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facebook")
@RequiredArgsConstructor
public class FacebookIntegrationController {
    
    private final FacebookService facebookService;
    private final SocialAccountService socialAccountService;
    
    /**
     * Get Facebook OAuth URL to redirect user for authorization
     */
    @GetMapping("/auth-url")
    public ResponseEntity<ApiResponse<String>> getAuthorizationUrl(
            @RequestParam Long clientId) {
        String authUrl = facebookService.getAuthorizationUrl(clientId);
        return ResponseEntity.ok(ApiResponse.success("Facebook authorization URL", authUrl));
    }
    
    /**
     * Callback endpoint after Facebook OAuth
     */
    @GetMapping("/callback")
    public ResponseEntity<ApiResponse<String>> handleCallback(
            @RequestParam String code,
            @RequestParam String state) {
        
        // Extract clientId from state parameter
        Long clientId = Long.parseLong(state);
        
        // Exchange code for access token and save pages
        facebookService.handleOAuthCallback(code, clientId);
        
        return ResponseEntity.ok(ApiResponse.success(
            "Facebook pages connected successfully. You can close this window.", 
            null
        ));
    }
    
    /**
     * Get list of Facebook pages user manages
     */
    @GetMapping("/pages")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getUserPages(
            @RequestParam String accessToken) {
        List<Map<String, Object>> pages = facebookService.getUserPages(accessToken);
        return ResponseEntity.ok(ApiResponse.success(pages));
    }
    
    /**
     * Connect a specific Facebook page
     */
    @PostMapping("/connect-page")
    public ResponseEntity<ApiResponse<SocialAccount>> connectPage(
            @RequestParam Long clientId,
            @RequestParam String pageId,
            @RequestParam String pageAccessToken,
            @RequestParam String pageName) {
        
        SocialAccount account = facebookService.connectPage(
            clientId, pageId, pageAccessToken, pageName
        );
        
        return ResponseEntity.ok(ApiResponse.success(
            "Facebook page connected successfully", 
            account
        ));
    }
    
    /**
     * Publish a post to Facebook page
     */
    @PostMapping("/publish")
    public ResponseEntity<ApiResponse<Map<String, String>>> publishPost(
            @RequestParam Long socialAccountId,
            @RequestParam String message,
            @RequestParam(required = false) String imageUrl) {
        
        Map<String, String> result = facebookService.publishPost(
            socialAccountId, message, imageUrl
        );
        
        return ResponseEntity.ok(ApiResponse.success(
            "Post published to Facebook successfully", 
            result
        ));
    }
    
    /**
     * Get page insights (analytics)
     */
    @GetMapping("/insights")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPageInsights(
            @RequestParam Long socialAccountId) {
        
        Map<String, Object> insights = facebookService.getPageInsights(socialAccountId);
        
        return ResponseEntity.ok(ApiResponse.success(insights));
    }
    
    /**
     * Disconnect Facebook page
     */
    @DeleteMapping("/disconnect/{socialAccountId}")
    public ResponseEntity<ApiResponse<String>> disconnectPage(
            @PathVariable Long socialAccountId) {
        
        facebookService.disconnectPage(socialAccountId);
        
        return ResponseEntity.ok(ApiResponse.success(
            "Facebook page disconnected successfully", 
            null
        ));
    }
}
