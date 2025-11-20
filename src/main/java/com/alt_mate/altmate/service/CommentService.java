package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.Comment;
import com.alt_mate.altmate.model.CommentStatus;
import com.alt_mate.altmate.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    
    @Transactional
    public Comment createComment(Comment comment) {
        comment.setCommentedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }
    
    @Transactional
    public Comment updateCommentStatus(Long commentId, CommentStatus status) {
        Comment comment = getCommentById(commentId);
        comment.setStatus(status);
        
        if (status == CommentStatus.REPLIED) {
            comment.setRespondedAt(LocalDateTime.now());
        }
        
        return commentRepository.save(comment);
    }
    
    @Transactional
    public Comment respondToComment(Long commentId, Long userId, String responseText) {
        Comment comment = getCommentById(commentId);
        comment.setStatus(CommentStatus.REPLIED);
        comment.setResponseText(responseText);
        comment.setRespondedAt(LocalDateTime.now());
        // Set respondedBy using UserService
        return commentRepository.save(comment);
    }
    
    @Transactional
    public Comment respondToComment(Long commentId, String responseText) {
        Comment comment = getCommentById(commentId);
        comment.setStatus(CommentStatus.REPLIED);
        comment.setResponseText(responseText);
        comment.setRespondedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }
    
    public List<Comment> getPendingComments() {
        return commentRepository.findByStatus(CommentStatus.PENDING);
    }
    
    @Transactional
    public Comment updateComment(Long commentId, Comment commentDetails) {
        Comment comment = getCommentById(commentId);
        comment.setCommentText(commentDetails.getCommentText());
        comment.setStatus(commentDetails.getStatus());
        return commentRepository.save(comment);
    }
    
    @Transactional
    public Comment approveComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setStatus(CommentStatus.REPLIED);
        return commentRepository.save(comment);
    }
    
    @Transactional
    public Comment hideComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setStatus(CommentStatus.HIDDEN);
        return commentRepository.save(comment);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }
    
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }
    
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    
    public List<Comment> getCommentsByStatus(CommentStatus status) {
        return commentRepository.findByStatus(status);
    }
    
    public List<Comment> getCommentsByResponder(Long userId) {
        return commentRepository.findByRespondedById(userId);
    }
    
    public List<Comment> getCommentsByPostAndStatus(Long postId, CommentStatus status) {
        return commentRepository.findByPostIdAndStatus(postId, status);
    }
    
    public List<Comment> getCommentsByClient(Long clientId) {
        return commentRepository.findByClientId(clientId);
    }
    
    public List<Comment> getCommentsByClientAndStatus(Long clientId, CommentStatus status) {
        return commentRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<Comment> getCommentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.findByCommentedAtBetween(startDate, endDate);
    }
    
    public Long countPendingComments() {
        return commentRepository.countPendingComments();
    }
    
    public Long countCommentsByPost(Long postId) {
        return commentRepository.countCommentsByPost(postId);
    }
}
