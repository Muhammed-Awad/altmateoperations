package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.Post;
import com.example.altmate_operations.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByClientId(Long clientId);
    
    List<Post> findBySocialAccountId(Long socialAccountId);
    
    List<Post> findByStatus(PostStatus status);
    
    List<Post> findByCreatedById(Long userId);
    
    List<Post> findByClientIdAndStatus(Long clientId, PostStatus status);
    
    List<Post> findBySocialAccountIdAndStatus(Long socialAccountId, PostStatus status);
    
    @Query("SELECT p FROM Post p WHERE p.scheduledAt BETWEEN :startDate AND :endDate")
    List<Post> findByScheduledAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Post p WHERE p.publishedAt BETWEEN :startDate AND :endDate")
    List<Post> findByPublishedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Post p WHERE p.status = :status AND p.scheduledAt <= :currentTime")
    List<Post> findScheduledPostsDue(@Param("status") PostStatus status, 
                                     @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT p FROM Post p WHERE p.client.id = :clientId ORDER BY p.engagementRate DESC")
    List<Post> findTopPerformingPostsByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT p FROM Post p WHERE p.socialAccount.id = :accountId ORDER BY p.likes DESC")
    List<Post> findTopLikedPostsByAccount(@Param("accountId") Long accountId);
    
    @Query("SELECT COUNT(p) FROM Post p WHERE p.status = :status")
    Long countByStatus(@Param("status") PostStatus status);
    
    @Query("SELECT SUM(p.reach) FROM Post p WHERE p.client.id = :clientId AND p.publishedAt BETWEEN :startDate AND :endDate")
    Long getTotalReachByClientAndDateRange(@Param("clientId") Long clientId, 
                                           @Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
}
