package com.example.altmate_operations.service;

import com.example.altmate_operations.model.Post;
import com.example.altmate_operations.model.PostStatus;
import com.example.altmate_operations.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    
    @Transactional
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post updatePost(Long postId, Post postDetails) {
        Post post = getPostById(postId);
        post.setContent(postDetails.getContent());
        post.setHook(postDetails.getHook());
        post.setSlogan(postDetails.getSlogan());
        post.setMediaUrls(postDetails.getMediaUrls());
        post.setScheduledAt(postDetails.getScheduledAt());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post updatePostStatus(Long postId, PostStatus status) {
        Post post = getPostById(postId);
        post.setStatus(status);
        
        if (status == PostStatus.PUBLISHED) {
            post.setPublishedAt(LocalDateTime.now());
        }
        
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post schedulePost(Long postId, LocalDateTime scheduledAt) {
        Post post = getPostById(postId);
        post.setScheduledAt(scheduledAt);
        post.setStatus(PostStatus.SCHEDULED);
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post updatePostAnalytics(Long postId, Integer reach, Integer impressions, 
                                    Integer likes, Integer comments, Integer shares) {
        Post post = getPostById(postId);
        post.setReach(reach);
        post.setImpressions(impressions);
        post.setLikes(likes);
        post.setComments(comments);
        post.setShares(shares);
        
        // Calculate engagement rate
        if (impressions > 0) {
            double engagementRate = ((likes + comments + shares) * 100.0) / impressions;
            post.setEngagementRate(engagementRate);
        }
        
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post addMediaUrl(Long postId, String mediaUrl) {
        Post post = getPostById(postId);
        post.getMediaUrls().add(mediaUrl);
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public Post removeMediaUrl(Long postId, String mediaUrl) {
        Post post = getPostById(postId);
        post.getMediaUrls().remove(mediaUrl);
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Transactional
    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
    }
    
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public List<Post> getPostsByClient(Long clientId) {
        return postRepository.findByClientId(clientId);
    }
    
    public List<Post> getPostsBySocialAccount(Long socialAccountId) {
        return postRepository.findBySocialAccountId(socialAccountId);
    }
    
    public List<Post> getPostsByStatus(PostStatus status) {
        return postRepository.findByStatus(status);
    }
    
    public List<Post> getPostsByCreator(Long userId) {
        return postRepository.findByCreatedById(userId);
    }
    
    public List<Post> getPostsByClientAndStatus(Long clientId, PostStatus status) {
        return postRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<Post> getPostsByAccountAndStatus(Long socialAccountId, PostStatus status) {
        return postRepository.findBySocialAccountIdAndStatus(socialAccountId, status);
    }
    
    public List<Post> getPostsByScheduledDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByScheduledAtBetween(startDate, endDate);
    }
    
    public List<Post> getPostsByPublishedDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByPublishedAtBetween(startDate, endDate);
    }
    
    public List<Post> getScheduledPostsDue() {
        return postRepository.findScheduledPostsDue(PostStatus.SCHEDULED, LocalDateTime.now());
    }
    
    public List<Post> getTopPerformingPostsByClient(Long clientId) {
        return postRepository.findTopPerformingPostsByClient(clientId);
    }
    
    public List<Post> getTopLikedPostsByAccount(Long accountId) {
        return postRepository.findTopLikedPostsByAccount(accountId);
    }
    
    public Long countPostsByStatus(PostStatus status) {
        return postRepository.countByStatus(status);
    }
    
    public Long getTotalReachByClientAndDateRange(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.getTotalReachByClientAndDateRange(clientId, startDate, endDate);
    }
}
