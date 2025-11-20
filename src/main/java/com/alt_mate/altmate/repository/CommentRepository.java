package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.Comment;
import com.example.altmate_operations.model.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByPostId(Long postId);
    
    List<Comment> findByStatus(CommentStatus status);
    
    List<Comment> findByRespondedById(Long userId);
    
    List<Comment> findByPostIdAndStatus(Long postId, CommentStatus status);
    
    @Query("SELECT c FROM Comment c WHERE c.post.client.id = :clientId")
    List<Comment> findByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT c FROM Comment c WHERE c.post.client.id = :clientId AND c.status = :status")
    List<Comment> findByClientIdAndStatus(@Param("clientId") Long clientId, 
                                          @Param("status") CommentStatus status);
    
    @Query("SELECT c FROM Comment c WHERE c.commentedAt BETWEEN :startDate AND :endDate")
    List<Comment> findByCommentedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.status = 'PENDING'")
    Long countPendingComments();
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countCommentsByPost(@Param("postId") Long postId);
}
