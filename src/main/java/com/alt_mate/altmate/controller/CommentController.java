package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.DTO.CommentCreateRequest;
import com.alt_mate.altmate.DTO.CommentDTO;
import com.alt_mate.altmate.mapper.CommentMapper;
import com.alt_mate.altmate.model.Comment;
import com.alt_mate.altmate.model.CommentStatus;
import com.alt_mate.altmate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(@Valid @RequestBody CommentCreateRequest request) {
        Comment comment = commentMapper.toEntity(request);
        Comment createdComment = commentService.createComment(comment);
        CommentDTO dto = commentMapper.toDTO(createdComment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Comment created successfully", dto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        CommentDTO dto = commentMapper.toDTO(comment);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDTO> dtos = commentMapper.toDTOList(comments);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        List<CommentDTO> dtos = commentMapper.toDTOList(comments);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByStatus(@PathVariable CommentStatus status) {
        List<Comment> comments = commentService.getCommentsByStatus(status);
        List<CommentDTO> dtos = commentMapper.toDTOList(comments);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getPendingComments() {
        List<Comment> comments = commentService.getPendingComments();
        List<CommentDTO> dtos = commentMapper.toDTOList(comments);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> updateComment(
            @PathVariable Long id,
            @RequestBody Comment commentDetails) {
        Comment updatedComment = commentService.updateComment(id, commentDetails);
        CommentDTO dto = commentMapper.toDTO(updatedComment);
        return ResponseEntity.ok(ApiResponse.success("Comment updated successfully", dto));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CommentDTO>> updateCommentStatus(
            @PathVariable Long id,
            @RequestParam CommentStatus status) {
        Comment comment = commentService.updateCommentStatus(id, status);
        CommentDTO dto = commentMapper.toDTO(comment);
        return ResponseEntity.ok(ApiResponse.success("Comment status updated successfully", dto));
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CommentDTO>> approveComment(@PathVariable Long id) {
        Comment comment = commentService.approveComment(id);
        CommentDTO dto = commentMapper.toDTO(comment);
        return ResponseEntity.ok(ApiResponse.success("Comment approved successfully", dto));
    }
    
    @PutMapping("/{id}/hide")
    public ResponseEntity<ApiResponse<CommentDTO>> hideComment(@PathVariable Long id) {
        Comment comment = commentService.hideComment(id);
        CommentDTO dto = commentMapper.toDTO(comment);
        return ResponseEntity.ok(ApiResponse.success("Comment hidden successfully", dto));
    }
    
    @PutMapping("/{id}/respond")
    public ResponseEntity<ApiResponse<CommentDTO>> respondToComment(
            @PathVariable Long id,
            @RequestParam String response) {
        Comment comment = commentService.respondToComment(id, response);
        CommentDTO dto = commentMapper.toDTO(comment);
        return ResponseEntity.ok(ApiResponse.success("Response posted successfully", dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully", null));
    }
    
    @GetMapping("/count/pending")
    public ResponseEntity<ApiResponse<Long>> countPendingComments() {
        Long count = commentService.countPendingComments();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
