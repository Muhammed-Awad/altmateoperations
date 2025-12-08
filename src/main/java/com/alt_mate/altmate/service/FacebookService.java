package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.SocialPlatform;
import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.repository.SocialAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FacebookService {
    
    private final SocialAccountRepository socialAccountRepository;
    private final ClientService clientService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${spring.security.oauth2.client.registration.facebook.client-id:}")
    private String facebookAppId;
    
    @Value("${spring.security.oauth2.client.registration.facebook.client-secret:}")
    private String facebookAppSecret;
    
    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri:http://localhost:8081/api/facebook/callback}")
    private String redirectUri;
    
    private static final String GRAPH_API_BASE = "https://graph.facebook.com/v18.0";
    
    /**
     * Generate Facebook OAuth authorization URL
     */
    public String getAuthorizationUrl(Long clientId) {
        String scope = "pages_show_list,pages_read_engagement,pages_manage_posts,pages_manage_metadata,public_profile";
        
        return String.format(
            "https://www.facebook.com/v18.0/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s&state=%s",
            facebookAppId,
            redirectUri,
            scope,
            clientId // Pass clientId as state parameter
        );
    }
    
    /**
     * Handle OAuth callback and exchange code for access token
     */
    public void handleOAuthCallback(String code, Long clientId) {
        // Exchange authorization code for access token
        String accessToken = exchangeCodeForToken(code);
        
        // Get user's Facebook pages
        List<Map<String, Object>> pages = getUserPages(accessToken);
        
        // Save all pages as social accounts
        Client client = clientService.getClientById(clientId);
        
        for (Map<String, Object> page : pages) {
            String pageId = (String) page.get("id");
            String pageName = (String) page.get("name");
            String pageAccessToken = (String) page.get("access_token");
            
            // Check if page already connected
            Optional<SocialAccount> existing = socialAccountRepository
                .findByPlatformAndAccountId(SocialPlatform.FACEBOOK, pageId);
            
            if (existing.isEmpty()) {
                SocialAccount account = new SocialAccount();
                account.setClient(client);
                account.setPlatform(SocialPlatform.FACEBOOK);
                account.setAccountId(pageId);
                account.setAccountName(pageName);
                account.setAccountUsername(pageName.toLowerCase().replaceAll("\\s+", "_"));
                account.setAccessToken(pageAccessToken);
                account.setTokenExpiresAt(LocalDateTime.now().plusYears(1)); // Long-lived token
                account.setIsActive(true);
                account.setConnectedAt(LocalDateTime.now());
                account.setLastSyncedAt(LocalDateTime.now());
                
                socialAccountRepository.save(account);
            }
        }
    }
    
    /**
     * Exchange authorization code for access token
     */
    private String exchangeCodeForToken(String code) {
        String url = String.format(
            "%s/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
            GRAPH_API_BASE,
            facebookAppId,
            facebookAppSecret,
            redirectUri,
            code
        );
        
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (String) response.getBody().get("access_token");
    }
    
    /**
     * Get list of Facebook pages the user manages
     */
    public List<Map<String, Object>> getUserPages(String accessToken) {
        String url = String.format(
            "%s/me/accounts?access_token=%s",
            GRAPH_API_BASE,
            accessToken
        );
        
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Map<String, Object>>) response.getBody().get("data");
    }
    
    /**
     * Connect a specific Facebook page
     */
    public SocialAccount connectPage(Long clientId, String pageId, String pageAccessToken, String pageName) {
        Client client = clientService.getClientById(clientId);
        
        // Check if already exists
        Optional<SocialAccount> existing = socialAccountRepository
            .findByPlatformAndAccountId(SocialPlatform.FACEBOOK, pageId);
        
        if (existing.isPresent()) {
            // Update existing
            SocialAccount account = existing.get();
            account.setAccessToken(pageAccessToken);
            account.setIsActive(true);
            account.setLastSyncedAt(LocalDateTime.now());
            return socialAccountRepository.save(account);
        }
        
        // Create new
        SocialAccount account = new SocialAccount();
        account.setClient(client);
        account.setPlatform(SocialPlatform.FACEBOOK);
        account.setAccountId(pageId);
        account.setAccountName(pageName);
        account.setAccountUsername(pageName.toLowerCase().replaceAll("\\s+", "_"));
        account.setAccessToken(pageAccessToken);
        account.setTokenExpiresAt(LocalDateTime.now().plusYears(1));
        account.setIsActive(true);
        account.setConnectedAt(LocalDateTime.now());
        account.setLastSyncedAt(LocalDateTime.now());
        
        return socialAccountRepository.save(account);
    }
    
    /**
     * Publish a post to Facebook page
     */
    public Map<String, String> publishPost(Long socialAccountId, String message, String imageUrl) {
        SocialAccount account = socialAccountRepository.findById(socialAccountId)
            .orElseThrow(() -> new RuntimeException("Social account not found"));
        
        String url = String.format(
            "%s/%s/feed",
            GRAPH_API_BASE,
            account.getAccountId()
        );
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        body.put("access_token", account.getAccessToken());
        
        if (imageUrl != null && !imageUrl.isEmpty()) {
            body.put("link", imageUrl);
        }
        
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) response.getBody().get("id"));
        result.put("message", "Post published successfully");
        
        return result;
    }
    
    /**
     * Get page insights (analytics)
     */
    public Map<String, Object> getPageInsights(Long socialAccountId) {
        SocialAccount account = socialAccountRepository.findById(socialAccountId)
            .orElseThrow(() -> new RuntimeException("Social account not found"));
        
        String url = String.format(
            "%s/%s?fields=followers_count,fan_count,name&access_token=%s",
            GRAPH_API_BASE,
            account.getAccountId(),
            account.getAccessToken()
        );
        
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
    
    /**
     * Disconnect Facebook page
     */
    public void disconnectPage(Long socialAccountId) {
        SocialAccount account = socialAccountRepository.findById(socialAccountId)
            .orElseThrow(() -> new RuntimeException("Social account not found"));
        
        account.setIsActive(false);
        account.setLastSyncedAt(LocalDateTime.now());
        socialAccountRepository.save(account);
    }
}
