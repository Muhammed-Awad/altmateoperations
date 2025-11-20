package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.ContentGenerationRequest;
import com.alt_mate.altmate.model.GenerationStatus;
import com.alt_mate.altmate.repository.ContentGenerationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentGenerationRequestService {
    
    private final ContentGenerationRequestRepository contentGenerationRequestRepository;
    
    @Transactional
    public ContentGenerationRequest createRequest(ContentGenerationRequest request) {
        request.setRequestedAt(LocalDateTime.now());
        return contentGenerationRequestRepository.save(request);
    }
    
    @Transactional
    public ContentGenerationRequest updateRequest(Long requestId, ContentGenerationRequest requestDetails) {
        ContentGenerationRequest request = getRequestById(requestId);
        request.setContentType(requestDetails.getContentType());
        request.setPrompt(requestDetails.getPrompt());
        request.setGeneratedContent(requestDetails.getGeneratedContent());
        return contentGenerationRequestRepository.save(request);
    }
    
    @Transactional
    public ContentGenerationRequest updateRequestStatus(Long requestId, GenerationStatus status) {
        ContentGenerationRequest request = getRequestById(requestId);
        request.setStatus(status);
        
        if (status == GenerationStatus.COMPLETED) {
            request.setGeneratedAt(LocalDateTime.now());
        }
        
        return contentGenerationRequestRepository.save(request);
    }
    
    @Transactional
    public ContentGenerationRequest completeRequest(Long requestId, String generatedContent) {
        ContentGenerationRequest request = getRequestById(requestId);
        request.setGeneratedContent(generatedContent);
        request.setStatus(GenerationStatus.COMPLETED);
        request.setGeneratedAt(LocalDateTime.now());
        return contentGenerationRequestRepository.save(request);
    }
    
    @Transactional
    public void deleteRequest(Long requestId) {
        ContentGenerationRequest request = getRequestById(requestId);
        contentGenerationRequestRepository.delete(request);
    }
    
    public ContentGenerationRequest getRequestById(Long requestId) {
        return contentGenerationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Content generation request not found with id: " + requestId));
    }
    
    public List<ContentGenerationRequest> getAllRequests() {
        return contentGenerationRequestRepository.findAll();
    }
    
    public List<ContentGenerationRequest> getRequestsByClient(Long clientId) {
        return contentGenerationRequestRepository.findByClientId(clientId);
    }
    
    public List<ContentGenerationRequest> getRequestsByStatus(GenerationStatus status) {
        return contentGenerationRequestRepository.findByStatus(status);
    }
    
    public List<ContentGenerationRequest> getPendingRequests() {
        return contentGenerationRequestRepository.findByStatus(GenerationStatus.PENDING);
    }
    
    public List<ContentGenerationRequest> getRequestsByRequester(Long userId) {
        return contentGenerationRequestRepository.findByRequestedById(userId);
    }
    
    public List<ContentGenerationRequest> getRequestsByClientAndStatus(Long clientId, GenerationStatus status) {
        return contentGenerationRequestRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<ContentGenerationRequest> getRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return contentGenerationRequestRepository.findByRequestedAtBetween(startDate, endDate);
    }
    
    public Long countPendingRequests() {
        return contentGenerationRequestRepository.countPendingRequests();
    }
    
    public List<ContentGenerationRequest> getPendingRequestsOrderedByDate() {
        return contentGenerationRequestRepository.findPendingRequestsOrderByDate();
    }
}
