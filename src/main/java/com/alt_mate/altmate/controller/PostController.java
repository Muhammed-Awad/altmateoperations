package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.*;
import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.Post;
import com.alt_mate.altmate.model.PostStatus;
import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.service.ClientService;
import com.alt_mate.altmate.service.PostService;
import com.alt_mate.altmate.service.SocialAccountService;
import com.alt_mate.altmate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    private final ClientService clientService;
    private final SocialAccountService socialAccountService;
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@Valid @RequestBody PostCreateRequest request) {
        Post post = mapToEntity(request);
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Post created successfully", mapToDTO(createdPost)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(post)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByClient(@PathVariable Long clientId) {
        List<Post> posts = postService.getPostsByClient(clientId);
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByStatus(@PathVariable PostStatus status) {
        List<Post> posts = postService.getPostsByStatus(status);
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsBySocialAccount(@PathVariable Long accountId) {
        List<Post> posts = postService.getPostsBySocialAccount(accountId);
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @GetMapping("/scheduled-due")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getScheduledPostsDue() {
        List<Post> posts = postService.getScheduledPostsDue();
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @GetMapping("/top-performing/client/{clientId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getTopPerformingPostsByClient(@PathVariable Long clientId) {
        List<Post> posts = postService.getTopPerformingPostsByClient(clientId);
        List<PostDTO> postDTOs = posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(postDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostCreateRequest request) {
        Post postDetails = mapToEntity(request);
        Post updatedPost = postService.updatePost(id, postDetails);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", mapToDTO(updatedPost)));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PostDTO>> updatePostStatus(
            @PathVariable Long id,
            @RequestParam PostStatus status) {
        Post post = postService.updatePostStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Post status updated successfully", mapToDTO(post)));
    }
    
    @PutMapping("/{id}/schedule")
    public ResponseEntity<ApiResponse<PostDTO>> schedulePost(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledAt) {
        Post post = postService.schedulePost(id, scheduledAt);
        return ResponseEntity.ok(ApiResponse.success("Post scheduled successfully", mapToDTO(post)));
    }
    
    @PutMapping("/{id}/analytics")
    public ResponseEntity<ApiResponse<PostDTO>> updatePostAnalytics(
            @PathVariable Long id,
            @Valid @RequestBody PostAnalyticsRequest request) {
        Post post = postService.updatePostAnalytics(id, request.getReach(), request.getImpressions(),
                request.getLikes(), request.getComments(), request.getShares());
        return ResponseEntity.ok(ApiResponse.success("Post analytics updated successfully", mapToDTO(post)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success("Post deleted successfully", null));
    }
    
    @GetMapping("/count/status/{status}")
    public ResponseEntity<ApiResponse<Long>> countPostsByStatus(@PathVariable PostStatus status) {
        Long count = postService.countPostsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(count));
    }
    
    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setHook(post.getHook());
        dto.setSlogan(post.getSlogan());
        dto.setMediaUrls(post.getMediaUrls());
        dto.setStatus(post.getStatus());
        dto.setScheduledAt(post.getScheduledAt());
        dto.setPublishedAt(post.getPublishedAt());
        dto.setReach(post.getReach());
        dto.setImpressions(post.getImpressions());
        dto.setLikes(post.getLikes());
        dto.setComments(post.getComments());
        dto.setShares(post.getShares());
        dto.setEngagementRate(post.getEngagementRate());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        
        if (post.getClient() != null) {
            dto.setClientId(post.getClient().getId());
            dto.setClientName(post.getClient().getName());
        }
        
        if (post.getSocialAccount() != null) {
            dto.setSocialAccountId(post.getSocialAccount().getId());
            dto.setSocialAccountName(post.getSocialAccount().getAccountUsername());
        }
        
        if (post.getCreatedBy() != null) {
            dto.setCreatedById(post.getCreatedBy().getId());
            dto.setCreatedByName(post.getCreatedBy().getFullname());
        }
        
        return dto;
    }
    
    private Post mapToEntity(PostCreateRequest request) {
        Post post = new Post();
        post.setContent(request.getContent());
        post.setHook(request.getHook());
        post.setSlogan(request.getSlogan());
        post.setMediaUrls(request.getMediaUrls());
        post.setStatus(request.getStatus());
        post.setScheduledAt(request.getScheduledAt());
        
        // Set Client relationship
        if (request.getClientId() != null) {
            Client client = clientService.getClientById(request.getClientId());
            post.setClient(client);
        }
        
        // Set SocialAccount relationship
        if (request.getSocialAccountId() != null) {
            SocialAccount account = socialAccountService.getSocialAccountById(request.getSocialAccountId());
            post.setSocialAccount(account);
        }
        
        // Set CreatedBy relationship
        if (request.getCreatedById() != null) {
            User createdBy = userService.getUserById(request.getCreatedById());
            post.setCreatedBy(createdBy);
        }
        
        return post;
    }
}
