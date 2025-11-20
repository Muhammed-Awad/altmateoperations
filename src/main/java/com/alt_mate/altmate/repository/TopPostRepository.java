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
    
    List<TopPost> findByClientId(Long clientId);
    
    List<TopPost> findBySocialAccountId(Long socialAccountId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.trackedAt BETWEEN :startDate AND :endDate")
    List<TopPost> findByTrackedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.client.id = :clientId ORDER BY tp.engagementRate DESC")
    List<TopPost> findTopPerformingByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.socialAccount.id = :accountId ORDER BY tp.likes DESC")
    List<TopPost> findTopLikedByAccount(@Param("accountId") Long accountId);
    
    @Query("SELECT tp FROM TopPost tp WHERE tp.client.id = :clientId AND tp.trackedAt BETWEEN :startDate AND :endDate ORDER BY tp.engagementRate DESC")
    List<TopPost> findTopPerformingByClientAndDateRange(@Param("clientId") Long clientId, 
                                                        @Param("startDate") LocalDateTime startDate, 
                                                        @Param("endDate") LocalDateTime endDate);
}
