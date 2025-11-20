package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.AdCampaign;
import com.alt_mate.altmate.model.CampaignObjective;
import com.alt_mate.altmate.model.CampaignStatus;
import com.alt_mate.altmate.repository.AdCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdCampaignService {
    
    private final AdCampaignRepository adCampaignRepository;
    
    @Transactional
    public AdCampaign createCampaign(AdCampaign campaign) {
        campaign.setSpentAmount(BigDecimal.ZERO);
        campaign.setRemainingBudget(campaign.getTotalBudget());
        campaign.setCreatedAt(LocalDateTime.now());
        campaign.setUpdatedAt(LocalDateTime.now());
        return adCampaignRepository.save(campaign);
    }
    
    @Transactional
    public AdCampaign updateCampaign(Long campaignId, AdCampaign campaignDetails) {
        AdCampaign campaign = getCampaignById(campaignId);
        campaign.setCampaignName(campaignDetails.getCampaignName());
        campaign.setObjective(campaignDetails.getObjective());
        campaign.setTotalBudget(campaignDetails.getTotalBudget());
        campaign.setAutoPauseEnabled(campaignDetails.getAutoPauseEnabled());
        campaign.setStartDate(campaignDetails.getStartDate());
        campaign.setEndDate(campaignDetails.getEndDate());
        campaign.setUpdatedAt(LocalDateTime.now());
        
        // Recalculate remaining budget
        campaign.setRemainingBudget(campaign.getTotalBudget().subtract(campaign.getSpentAmount()));
        
        return adCampaignRepository.save(campaign);
    }
    
    @Transactional
    public AdCampaign updateCampaignStatus(Long campaignId, CampaignStatus status) {
        AdCampaign campaign = getCampaignById(campaignId);
        campaign.setStatus(status);
        campaign.setUpdatedAt(LocalDateTime.now());
        return adCampaignRepository.save(campaign);
    }
    
    @Transactional
    public AdCampaign updateSpentAmount(Long campaignId, BigDecimal additionalSpent) {
        AdCampaign campaign = getCampaignById(campaignId);
        campaign.setSpentAmount(campaign.getSpentAmount().add(additionalSpent));
        campaign.setRemainingBudget(campaign.getTotalBudget().subtract(campaign.getSpentAmount()));
        campaign.setUpdatedAt(LocalDateTime.now());
        
        // Auto-pause if enabled and budget exhausted
        if (campaign.getAutoPauseEnabled() && 
            campaign.getSpentAmount().compareTo(campaign.getTotalBudget()) >= 0) {
            campaign.setStatus(CampaignStatus.PAUSED);
        }
        
        return adCampaignRepository.save(campaign);
    }
    
    @Transactional
    public AdCampaign updateCampaignMetrics(Long campaignId, Integer impressions, Integer clicks, 
                                           Integer conversions) {
        AdCampaign campaign = getCampaignById(campaignId);
        campaign.setImpressions(impressions);
        campaign.setClicks(clicks);
        campaign.setConversions(conversions);
        
        // Calculate cost metrics
        if (clicks > 0) {
            campaign.setCostPerClick(campaign.getSpentAmount().divide(BigDecimal.valueOf(clicks), 2, RoundingMode.HALF_UP));
        }
        if (conversions > 0) {
            campaign.setCostPerConversion(campaign.getSpentAmount().divide(BigDecimal.valueOf(conversions), 2, RoundingMode.HALF_UP));
        }
        
        campaign.setUpdatedAt(LocalDateTime.now());
        return adCampaignRepository.save(campaign);
    }
    
    @Transactional
    public void deleteCampaign(Long campaignId) {
        AdCampaign campaign = getCampaignById(campaignId);
        adCampaignRepository.delete(campaign);
    }
    
    @Transactional
    public void pauseExpiredCampaigns() {
        List<AdCampaign> expiredCampaigns = adCampaignRepository.findExpiredActiveCampaigns(LocalDateTime.now());
        for (AdCampaign campaign : expiredCampaigns) {
            campaign.setStatus(CampaignStatus.COMPLETED);
            campaign.setUpdatedAt(LocalDateTime.now());
            adCampaignRepository.save(campaign);
        }
    }
    
    @Transactional
    public void autoPauseBudgetExhaustedCampaigns() {
        List<AdCampaign> campaigns = adCampaignRepository.findCampaignsToAutoPause(CampaignStatus.ACTIVE);
        for (AdCampaign campaign : campaigns) {
            campaign.setStatus(CampaignStatus.PAUSED);
            campaign.setUpdatedAt(LocalDateTime.now());
            adCampaignRepository.save(campaign);
        }
    }
    
    public AdCampaign getCampaignById(Long campaignId) {
        return adCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + campaignId));
    }
    
    public List<AdCampaign> getAllCampaigns() {
        return adCampaignRepository.findAll();
    }
    
    public List<AdCampaign> getCampaignsByClient(Long clientId) {
        return adCampaignRepository.findByClientId(clientId);
    }
    
    public List<AdCampaign> getCampaignsBySocialAccount(Long socialAccountId) {
        return adCampaignRepository.findBySocialAccountId(socialAccountId);
    }
    
    public List<AdCampaign> getCampaignsByStatus(CampaignStatus status) {
        return adCampaignRepository.findByStatus(status);
    }
    
    public List<AdCampaign> getCampaignsByObjective(CampaignObjective objective) {
        return adCampaignRepository.findByObjective(objective);
    }
    
    public List<AdCampaign> getCampaignsByManager(Long userId) {
        return adCampaignRepository.findByManagedById(userId);
    }
    
    public List<AdCampaign> getCampaignsByClientAndStatus(Long clientId, CampaignStatus status) {
        return adCampaignRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<AdCampaign> getCampaignsByEndDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return adCampaignRepository.findByEndDateBetween(startDate, endDate);
    }
    
    public BigDecimal getTotalSpentByClient(Long clientId) {
        return adCampaignRepository.getTotalSpentByClient(clientId);
    }
    
    public BigDecimal getTotalSpentByClientAndStatus(Long clientId, CampaignStatus status) {
        return adCampaignRepository.getTotalSpentByClientAndStatus(clientId, status);
    }
    
    public Long countCampaignsByStatus(CampaignStatus status) {
        return adCampaignRepository.countByStatus(status);
    }
}
