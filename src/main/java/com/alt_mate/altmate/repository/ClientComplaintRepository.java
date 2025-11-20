package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.ClientComplaint;
import com.example.altmate_operations.model.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientComplaintRepository extends JpaRepository<ClientComplaint, Long> {
    
    List<ClientComplaint> findByClientId(Long clientId);
    
    List<ClientComplaint> findByStatus(ComplaintStatus status);
    
    List<ClientComplaint> findByResolvedById(Long userId);
    
    List<ClientComplaint> findByClientIdAndStatus(Long clientId, ComplaintStatus status);
    
    @Query("SELECT cc FROM ClientComplaint cc WHERE cc.submittedAt BETWEEN :startDate AND :endDate")
    List<ClientComplaint> findBySubmittedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT cc FROM ClientComplaint cc WHERE cc.status = 'OPEN' ORDER BY cc.submittedAt ASC")
    List<ClientComplaint> findOpenComplaintsOrderByDate();
    
    @Query("SELECT COUNT(cc) FROM ClientComplaint cc WHERE cc.status = 'OPEN'")
    Long countOpenComplaints();
    
    @Query("SELECT COUNT(cc) FROM ClientComplaint cc WHERE cc.client.id = :clientId AND cc.status = 'OPEN'")
    Long countOpenComplaintsByClient(@Param("clientId") Long clientId);
}
