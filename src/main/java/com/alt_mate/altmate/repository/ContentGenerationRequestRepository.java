package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.ContentGenerationRequest;
import com.example.altmate_operations.model.GenerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContentGenerationRequestRepository extends JpaRepository<ContentGenerationRequest, Long> {
    
    List<ContentGenerationRequest> findByClientId(Long clientId);
    
    List<ContentGenerationRequest> findByStatus(GenerationStatus status);
    
    List<ContentGenerationRequest> findByRequestedById(Long userId);
    
    List<ContentGenerationRequest> findByClientIdAndStatus(Long clientId, GenerationStatus status);
    
    @Query("SELECT cgr FROM ContentGenerationRequest cgr WHERE cgr.requestedAt BETWEEN :startDate AND :endDate")
    List<ContentGenerationRequest> findByRequestedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                                            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(cgr) FROM ContentGenerationRequest cgr WHERE cgr.status = 'PENDING'")
    Long countPendingRequests();
    
    @Query("SELECT cgr FROM ContentGenerationRequest cgr WHERE cgr.status = 'PENDING' ORDER BY cgr.requestedAt ASC")
    List<ContentGenerationRequest> findPendingRequestsOrderByDate();
}
