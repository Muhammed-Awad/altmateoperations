package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.CompetitorAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompetitorAnalysisRepository extends JpaRepository<CompetitorAnalysis, Long> {
    
    List<CompetitorAnalysis> findByClientId(Long clientId);
    
    List<CompetitorAnalysis> findByCompetitorName(String competitorName);
    
    @Query("SELECT ca FROM CompetitorAnalysis ca WHERE ca.analyzedAt BETWEEN :startDate AND :endDate")
    List<CompetitorAnalysis> findByAnalyzedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                                     @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ca FROM CompetitorAnalysis ca WHERE ca.client.id = :clientId ORDER BY ca.analyzedAt DESC")
    List<CompetitorAnalysis> findByClientIdOrderByAnalyzedAtDesc(@Param("clientId") Long clientId);
    
    @Query("SELECT ca FROM CompetitorAnalysis ca WHERE ca.client.id = :clientId AND ca.competitorName = :competitorName")
    List<CompetitorAnalysis> findByClientIdAndCompetitorName(@Param("clientId") Long clientId, 
                                                             @Param("competitorName") String competitorName);
}
