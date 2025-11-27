package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.TopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopPostRepository extends JpaRepository<TopPost, Long> {
    
    List<TopPost> findByAnalysisId(Long analysisId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.publishedAt BETWEEN :startDate AND :endDate")
    List<TopPost> findByPublishedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.analysis.request.client.id = :clientId ORDER BY tp.totalEngagement DESC")
    List<TopPost> findTopPerformingByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.analysis.id = :analysisId ORDER BY tp.likes DESC")
    List<TopPost> findTopLikedByAnalysis(@Param("analysisId") Long analysisId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.analysis.request.client.id = :clientId AND tp.publishedAt BETWEEN :startDate AND :endDate ORDER BY tp.totalEngagement DESC")
    List<TopPost> findTopPerformingByClientAndDateRange(@Param("clientId") Long clientId, 
                                                        @Param("startDate") LocalDateTime startDate, 
                                                        @Param("endDate") LocalDateTime endDate);
}
