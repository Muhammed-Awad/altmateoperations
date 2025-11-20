package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.ContentGenerationRequest;
import com.alt_mate.altmate.model.GenerationStatus;
import com.alt_mate.altmate.service.ContentGenerationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-generation")
@RequiredArgsConstructor
public class ContentGenerationRequestController {
    
    private final ContentGenerationRequestService contentGenerationRequestService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ContentGenerationRequest>> createRequest(@RequestBody ContentGenerationRequest request) {
        ContentGenerationRequest createdRequest = contentGenerationRequestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Content generation request created successfully", createdRequest));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentGenerationRequest>> getRequestById(@PathVariable Long id) {
        ContentGenerationRequest request = contentGenerationRequestService.getRequestById(id);
        return ResponseEntity.ok(ApiResponse.success(request));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContentGenerationRequest>>> getAllRequests() {
        List<ContentGenerationRequest> requests = contentGenerationRequestService.getAllRequests();
        return ResponseEntity.ok(ApiResponse.success(requests));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<ContentGenerationRequest>>> getRequestsByClient(@PathVariable Long clientId) {
        List<ContentGenerationRequest> requests = contentGenerationRequestService.getRequestsByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(requests));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ContentGenerationRequest>>> getRequestsByStatus(@PathVariable GenerationStatus status) {
        List<ContentGenerationRequest> requests = contentGenerationRequestService.getRequestsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(requests));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<ContentGenerationRequest>>> getPendingRequests() {
        List<ContentGenerationRequest> requests = contentGenerationRequestService.getPendingRequests();
        return ResponseEntity.ok(ApiResponse.success(requests));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentGenerationRequest>> updateRequest(
            @PathVariable Long id,
            @RequestBody ContentGenerationRequest requestDetails) {
        ContentGenerationRequest updatedRequest = contentGenerationRequestService.updateRequest(id, requestDetails);
        return ResponseEntity.ok(ApiResponse.success("Content generation request updated successfully", updatedRequest));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ContentGenerationRequest>> updateRequestStatus(
            @PathVariable Long id,
            @RequestParam GenerationStatus status) {
        ContentGenerationRequest request = contentGenerationRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Request status updated successfully", request));
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<ContentGenerationRequest>> completeRequest(
            @PathVariable Long id,
            @RequestParam String generatedContent) {
        ContentGenerationRequest request = contentGenerationRequestService.completeRequest(id, generatedContent);
        return ResponseEntity.ok(ApiResponse.success("Request completed successfully", request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRequest(@PathVariable Long id) {
        contentGenerationRequestService.deleteRequest(id);
        return ResponseEntity.ok(ApiResponse.success("Content generation request deleted successfully", null));
    }
    
    @GetMapping("/count/pending")
    public ResponseEntity<ApiResponse<Long>> countPendingRequests() {
        Long count = contentGenerationRequestService.countPendingRequests();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
