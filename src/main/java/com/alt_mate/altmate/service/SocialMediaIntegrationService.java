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
public class SocialMediaIntegrationService {
    
    private final SocialAccountRepository socialAccountRepository;
    private final ClientService clientService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    // Facebook & Instagram (Meta platforms)
    @Value("${social.meta.app-id:}")
    private String metaAppId;
    
    @Value("${social.meta.app-secret:}")
    private String metaAppSecret;
    
    // Twitter/X
    @Value("${social.twitter.api-key:}")
    private String twitterApiKey;
    
    @Value("${social.twitter.api-secret:}")
    private String twitterApiSecret;
    
    // LinkedIn
    @Value("${social.linkedin.client-id:}")
    private String linkedinClientId;
    
    @Value("${social.linkedin.client-secret:}")
    private String linkedinClientSecret;
    
    // Snapchat
    @Value("${social.snapchat.client-id:}")
    private String snapchatClientId;
    
    @Value("${social.snapchat.client-secret:}")
    private String snapchatClientSecret;
    
    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;
    
    private static final String META_GRAPH_API = "https://graph.facebook.com/v18.0";
    private static final String TWITTER_API = "https://api.twitter.com/2";
    private static final String LINKEDIN_API = "https://api.linkedin.com/v2";
    private static final String SNAPCHAT_API = "https://adsapi.snapchat.com/v1";
    
    /**
     * Get OAuth authorization URL for any platform
     */
    public String getAuthorizationUrl(SocialPlatform platform, Long clientId) {
        String redirectUri = baseUrl + "/api/social/callback/" + platform.name().toLowerCase();
        
        switch (platform) {
            case FACEBOOK:
                return String.format(
                    "https://www.facebook.com/v18.0/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s&state=%s",
                    metaAppId,
                    redirectUri,
                    "pages_show_list,pages_read_engagement,pages_manage_posts,pages_manage_metadata,public_profile",
                    clientId
                );
                
            case INSTAGRAM:
                return String.format(
                    "https://api.instagram.com/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&state=%s",
                    metaAppId,
                    redirectUri,
                    "instagram_basic,instagram_content_publish,instagram_manage_comments,instagram_manage_insights",
                    clientId
                );
                
            case THREADS:
                return String.format(
                    "https://threads.net/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&state=%s",
                    metaAppId,
                    redirectUri,
                    "threads_basic,threads_content_publish,threads_manage_insights",
                    clientId
                );
                
            case TWITTER:
                return String.format(
                    "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s&state=%s&code_challenge=challenge&code_challenge_method=plain",
                    twitterApiKey,
                    redirectUri,
                    "tweet.read tweet.write users.read offline.access",
                    clientId
                );
                
            case LINKEDIN:
                return String.format(
                    "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=%s&redirect_uri=%s&scope=%s&state=%s",
                    linkedinClientId,
                    redirectUri,
                    "w_member_social r_organization_social w_organization_social r_basicprofile",
                    clientId
                );
                
            case SNAPCHAT:
                return String.format(
                    "https://accounts.snapchat.com/login/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s",
                    snapchatClientId,
                    redirectUri,
                    "snapchat-marketing-api",
                    clientId
                );
                
            default:
                throw new RuntimeException("Unsupported platform: " + platform);
        }
    }
    
    /**
     * Handle OAuth callback for any platform
     */
    public SocialAccount handleOAuthCallback(SocialPlatform platform, String code, Long clientId) {
        String accessToken = exchangeCodeForToken(platform, code);
        return saveAccountForPlatform(platform, accessToken, clientId);
    }
    
    /**
     * Exchange authorization code for access token
     */
    private String exchangeCodeForToken(SocialPlatform platform, String code) {
        String redirectUri = baseUrl + "/api/social/callback/" + platform.name().toLowerCase();
        String url;
        
        switch (platform) {
            case FACEBOOK:
            case INSTAGRAM:
            case THREADS:
                url = String.format(
                    "%s/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                    META_GRAPH_API, metaAppId, metaAppSecret, redirectUri, code
                );
                break;
                
            case TWITTER:
                url = "https://api.twitter.com/2/oauth2/token";
                HttpHeaders twitterHeaders = new HttpHeaders();
                twitterHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                twitterHeaders.setBasicAuth(twitterApiKey, twitterApiSecret);
                
                Map<String, String> twitterBody = new HashMap<>();
                twitterBody.put("code", code);
                twitterBody.put("grant_type", "authorization_code");
                twitterBody.put("redirect_uri", redirectUri);
                twitterBody.put("code_verifier", "challenge");
                
                HttpEntity<Map<String, String>> twitterRequest = new HttpEntity<>(twitterBody, twitterHeaders);
                ResponseEntity<Map> twitterResponse = restTemplate.postForEntity(url, twitterRequest, Map.class);
                return (String) twitterResponse.getBody().get("access_token");
                
            case LINKEDIN:
                url = String.format(
                    "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                    code, linkedinClientId, linkedinClientSecret, redirectUri
                );
                break;
                
            case SNAPCHAT:
                url = "https://accounts.snapchat.com/login/oauth2/access_token";
                HttpHeaders snapHeaders = new HttpHeaders();
                snapHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                
                Map<String, String> snapBody = new HashMap<>();
                snapBody.put("code", code);
                snapBody.put("client_id", snapchatClientId);
                snapBody.put("client_secret", snapchatClientSecret);
                snapBody.put("grant_type", "authorization_code");
                snapBody.put("redirect_uri", redirectUri);
                
                HttpEntity<Map<String, String>> snapRequest = new HttpEntity<>(snapBody, snapHeaders);
                ResponseEntity<Map> snapResponse = restTemplate.postForEntity(url, snapRequest, Map.class);
                return (String) snapResponse.getBody().get("access_token");
                
            default:
                throw new RuntimeException("Unsupported platform: " + platform);
        }
        
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (String) response.getBody().get("access_token");
    }
    
    /**
     * Save social account for platform
     */
    private SocialAccount saveAccountForPlatform(SocialPlatform platform, String accessToken, Long clientId) {
        Client client = clientService.getClientById(clientId);
        Map<String, String> accountInfo = getAccountInfo(platform, accessToken);
        
        String accountId = accountInfo.get("id");
        String accountName = accountInfo.get("name");
        String username = accountInfo.get("username");
        
        // Check if account already exists
        Optional<SocialAccount> existing = socialAccountRepository
            .findByPlatformAndAccountId(platform, accountId);
        
        if (existing.isPresent()) {
            SocialAccount account = existing.get();
            account.setAccessToken(accessToken);
            account.setIsActive(true);
            account.setLastSyncedAt(LocalDateTime.now());
            return socialAccountRepository.save(account);
        }
        
        // Create new account
        SocialAccount account = new SocialAccount();
        account.setClient(client);
        account.setPlatform(platform);
        account.setAccountId(accountId);
        account.setAccountName(accountName);
        account.setAccountUsername(username != null ? username : accountName.toLowerCase().replaceAll("\\s+", "_"));
        account.setAccessToken(accessToken);
        account.setTokenExpiresAt(LocalDateTime.now().plusMonths(2));
        account.setIsActive(true);
        account.setConnectedAt(LocalDateTime.now());
        account.setLastSyncedAt(LocalDateTime.now());
        
        return socialAccountRepository.save(account);
    }
    
    /**
     * Get account information from platform
     */
    private Map<String, String> getAccountInfo(SocialPlatform platform, String accessToken) {
        String url;
        Map<String, String> info = new HashMap<>();
        
        switch (platform) {
            case FACEBOOK:
                url = String.format("%s/me?fields=id,name&access_token=%s", META_GRAPH_API, accessToken);
                break;
                
            case INSTAGRAM:
                url = String.format("%s/me?fields=id,username&access_token=%s", META_GRAPH_API, accessToken);
                break;
                
            case THREADS:
                url = String.format("%s/me?fields=id,username,name&access_token=%s", META_GRAPH_API, accessToken);
                break;
                
            case TWITTER:
                url = String.format("%s/users/me", TWITTER_API);
                HttpHeaders twitterHeaders = new HttpHeaders();
                twitterHeaders.setBearerAuth(accessToken);
                HttpEntity<?> twitterRequest = new HttpEntity<>(twitterHeaders);
                ResponseEntity<Map> twitterResponse = restTemplate.exchange(url, HttpMethod.GET, twitterRequest, Map.class);
                Map<String, Object> twitterData = (Map<String, Object>) twitterResponse.getBody().get("data");
                info.put("id", (String) twitterData.get("id"));
                info.put("name", (String) twitterData.get("name"));
                info.put("username", (String) twitterData.get("username"));
                return info;
                
            case LINKEDIN:
                url = String.format("%s/me", LINKEDIN_API);
                HttpHeaders linkedinHeaders = new HttpHeaders();
                linkedinHeaders.setBearerAuth(accessToken);
                HttpEntity<?> linkedinRequest = new HttpEntity<>(linkedinHeaders);
                ResponseEntity<Map> linkedinResponse = restTemplate.exchange(url, HttpMethod.GET, linkedinRequest, Map.class);
                info.put("id", (String) linkedinResponse.getBody().get("id"));
                Map<String, Object> localizedName = (Map<String, Object>) linkedinResponse.getBody().get("localizedFirstName");
                info.put("name", localizedName != null ? localizedName.toString() : "LinkedIn User");
                return info;
                
            case SNAPCHAT:
                url = String.format("%s/me", SNAPCHAT_API);
                HttpHeaders snapHeaders = new HttpHeaders();
                snapHeaders.setBearerAuth(accessToken);
                HttpEntity<?> snapRequest = new HttpEntity<>(snapHeaders);
                ResponseEntity<Map> snapResponse = restTemplate.exchange(url, HttpMethod.GET, snapRequest, Map.class);
                Map<String, Object> snapData = (Map<String, Object>) snapResponse.getBody().get("me");
                info.put("id", (String) snapData.get("id"));
                info.put("name", (String) snapData.get("display_name"));
                return info;
                
            default:
                throw new RuntimeException("Unsupported platform: " + platform);
        }
        
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        info.put("id", (String) response.getBody().get("id"));
        info.put("name", (String) response.getBody().get("name"));
        info.put("username", (String) response.getBody().get("username"));
        
        return info;
    }
    
    /**
     * Publish post to any platform
     */
    public Map<String, String> publishPost(Long socialAccountId, String message, String mediaUrl) {
        SocialAccount account = socialAccountRepository.findById(socialAccountId)
            .orElseThrow(() -> new RuntimeException("Social account not found"));
        
        switch (account.getPlatform()) {
            case FACEBOOK:
                return publishToFacebook(account, message, mediaUrl);
            case INSTAGRAM:
                return publishToInstagram(account, message, mediaUrl);
            case THREADS:
                return publishToThreads(account, message, mediaUrl);
            case TWITTER:
                return publishToTwitter(account, message, mediaUrl);
            case LINKEDIN:
                return publishToLinkedIn(account, message, mediaUrl);
            case SNAPCHAT:
                return publishToSnapchat(account, message, mediaUrl);
            default:
                throw new RuntimeException("Unsupported platform: " + account.getPlatform());
        }
    }
    
    private Map<String, String> publishToFacebook(SocialAccount account, String message, String mediaUrl) {
        String url = String.format("%s/%s/feed", META_GRAPH_API, account.getAccountId());
        
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        body.put("access_token", account.getAccessToken());
        if (mediaUrl != null) body.put("link", mediaUrl);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);
        
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) response.getBody().get("id"));
        result.put("platform", "Facebook");
        return result;
    }
    
    private Map<String, String> publishToInstagram(SocialAccount account, String message, String mediaUrl) {
        // Instagram requires media URL
        if (mediaUrl == null) {
            throw new RuntimeException("Instagram posts require an image or video URL");
        }
        
        // Step 1: Create media container
        String containerUrl = String.format("%s/%s/media", META_GRAPH_API, account.getAccountId());
        Map<String, String> containerBody = new HashMap<>();
        containerBody.put("image_url", mediaUrl);
        containerBody.put("caption", message);
        containerBody.put("access_token", account.getAccessToken());
        
        ResponseEntity<Map> containerResponse = restTemplate.postForEntity(containerUrl, containerBody, Map.class);
        String containerId = (String) containerResponse.getBody().get("id");
        
        // Step 2: Publish media
        String publishUrl = String.format("%s/%s/media_publish", META_GRAPH_API, account.getAccountId());
        Map<String, String> publishBody = new HashMap<>();
        publishBody.put("creation_id", containerId);
        publishBody.put("access_token", account.getAccessToken());
        
        ResponseEntity<Map> response = restTemplate.postForEntity(publishUrl, publishBody, Map.class);
        
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) response.getBody().get("id"));
        result.put("platform", "Instagram");
        return result;
    }
    
    private Map<String, String> publishToThreads(SocialAccount account, String message, String mediaUrl) {
        String url = String.format("%s/%s/threads", META_GRAPH_API, account.getAccountId());
        
        Map<String, String> body = new HashMap<>();
        body.put("text", message);
        body.put("access_token", account.getAccessToken());
        if (mediaUrl != null) body.put("media_url", mediaUrl);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);
        
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) response.getBody().get("id"));
        result.put("platform", "Threads");
        return result;
    }
    
    private Map<String, String> publishToTwitter(SocialAccount account, String message, String mediaUrl) {
        String url = String.format("%s/tweets", TWITTER_API);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(account.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> body = new HashMap<>();
        body.put("text", message);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) data.get("id"));
        result.put("platform", "Twitter/X");
        return result;
    }
    
    private Map<String, String> publishToLinkedIn(SocialAccount account, String message, String mediaUrl) {
        String url = String.format("%s/ugcPosts", LINKEDIN_API);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(account.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> body = new HashMap<>();
        body.put("author", "urn:li:person:" + account.getAccountId());
        body.put("lifecycleState", "PUBLISHED");
        
        Map<String, Object> specificContent = new HashMap<>();
        Map<String, Object> shareContent = new HashMap<>();
        shareContent.put("shareCommentary", Map.of("text", message));
        shareContent.put("shareMediaCategory", "NONE");
        specificContent.put("com.linkedin.ugc.ShareContent", shareContent);
        body.put("specificContent", specificContent);
        
        Map<String, Object> visibility = new HashMap<>();
        visibility.put("com.linkedin.ugc.MemberNetworkVisibility", "PUBLIC");
        body.put("visibility", visibility);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        
        Map<String, String> result = new HashMap<>();
        result.put("postId", (String) response.getBody().get("id"));
        result.put("platform", "LinkedIn");
        return result;
    }
    
    private Map<String, String> publishToSnapchat(SocialAccount account, String message, String mediaUrl) {
        // Snapchat API is primarily for ads, not organic posts
        // This is a placeholder for ad creative creation
        throw new RuntimeException("Snapchat organic posting not supported. Use Snapchat Ads API for campaigns.");
    }
    
    /**
     * Get account analytics/insights
     */
    public Map<String, Object> getAccountInsights(Long socialAccountId) {
        SocialAccount account = socialAccountRepository.findById(socialAccountId)
            .orElseThrow(() -> new RuntimeException("Social account not found"));
        
        switch (account.getPlatform()) {
            case FACEBOOK:
                return getFacebookInsights(account);
            case INSTAGRAM:
                return getInstagramInsights(account);
            case TWITTER:
                return getTwitterInsights(account);
            case LINKEDIN:
                return getLinkedInInsights(account);
            default:
                Map<String, Object> basic = new HashMap<>();
                basic.put("message", "Insights not available for " + account.getPlatform());
                return basic;
        }
    }
    
    private Map<String, Object> getFacebookInsights(SocialAccount account) {
        String url = String.format(
            "%s/%s?fields=followers_count,fan_count,name&access_token=%s",
            META_GRAPH_API, account.getAccountId(), account.getAccessToken()
        );
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
    
    private Map<String, Object> getInstagramInsights(SocialAccount account) {
        String url = String.format(
            "%s/%s?fields=followers_count,media_count,username&access_token=%s",
            META_GRAPH_API, account.getAccountId(), account.getAccessToken()
        );
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
    
    private Map<String, Object> getTwitterInsights(SocialAccount account) {
        String url = String.format("%s/users/%s?user.fields=public_metrics", TWITTER_API, account.getAccountId());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(account.getAccessToken());
        HttpEntity<?> request = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return (Map<String, Object>) ((Map<String, Object>) response.getBody().get("data")).get("public_metrics");
    }
    
    private Map<String, Object> getLinkedInInsights(SocialAccount account) {
        Map<String, Object> insights = new HashMap<>();
        insights.put("message", "LinkedIn insights require organization access");
        return insights;
    }
}
