package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.DashboardSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DashboardSnapshotRepository extends JpaRepository<DashboardSnapshot, Long> {
    
    List<DashboardSnapshot> findByClientId(Long clientId);
    
    @Query("SELECT ds FROM DashboardSnapshot ds WHERE ds.snapshotDate BETWEEN :startDate AND :endDate")
    List<DashboardSnapshot> findBySnapshotDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                      @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ds FROM DashboardSnapshot ds WHERE ds.client.id = :clientId AND ds.snapshotDate BETWEEN :startDate AND :endDate ORDER BY ds.snapshotDate DESC")
    List<DashboardSnapshot> findByClientIdAndDateRangeOrderByDateDesc(@Param("clientId") Long clientId, 
                                                                       @Param("startDate") LocalDateTime startDate, 
                                                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ds FROM DashboardSnapshot ds WHERE ds.client.id = :clientId ORDER BY ds.snapshotDate DESC")
    List<DashboardSnapshot> findByClientIdOrderByDateDesc(@Param("clientId") Long clientId);
    
    @Query("SELECT ds FROM DashboardSnapshot ds WHERE ds.client.id = :clientId ORDER BY ds.snapshotDate DESC LIMIT 1")
    Optional<DashboardSnapshot> findLatestByClientId(@Param("clientId") Long clientId);
}
