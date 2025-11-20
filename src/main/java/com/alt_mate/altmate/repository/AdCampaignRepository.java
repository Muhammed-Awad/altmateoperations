package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.AdCampaign;
import com.example.altmate_operations.model.CampaignStatus;
import com.example.altmate_operations.model.CampaignObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdCampaignRepository extends JpaRepository<AdCampaign, Long> {
    
    List<AdCampaign> findByClientId(Long clientId);
    
    List<AdCampaign> findBySocialAccountId(Long socialAccountId);
    
    List<AdCampaign> findByStatus(CampaignStatus status);
    
    List<AdCampaign> findByObjective(CampaignObjective objective);
    
    List<AdCampaign> findByManagedById(Long userId);
    
    List<AdCampaign> findByClientIdAndStatus(Long clientId, CampaignStatus status);
    
    @Query("SELECT ac FROM AdCampaign ac WHERE ac.endDate BETWEEN :startDate AND :endDate")
    List<AdCampaign> findByEndDateBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ac FROM AdCampaign ac WHERE ac.status = :status AND ac.autoPauseEnabled = true AND ac.spentAmount >= ac.totalBudget")
    List<AdCampaign> findCampaignsToAutoPause(@Param("status") CampaignStatus status);
    
    @Query("SELECT ac FROM AdCampaign ac WHERE ac.status = 'ACTIVE' AND ac.endDate < :currentDate")
    List<AdCampaign> findExpiredActiveCampaigns(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT SUM(ac.spentAmount) FROM AdCampaign ac WHERE ac.client.id = :clientId")
    BigDecimal getTotalSpentByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT SUM(ac.spentAmount) FROM AdCampaign ac WHERE ac.client.id = :clientId AND ac.status = :status")
    BigDecimal getTotalSpentByClientAndStatus(@Param("clientId") Long clientId, 
                                               @Param("status") CampaignStatus status);
    
    @Query("SELECT COUNT(ac) FROM AdCampaign ac WHERE ac.status = :status")
    Long countByStatus(@Param("status") CampaignStatus status);
}
