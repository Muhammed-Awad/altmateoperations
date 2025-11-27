package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.*;
import com.alt_mate.altmate.model.AdCampaign;
import com.alt_mate.altmate.model.CampaignStatus;
import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.service.AdCampaignService;
import com.alt_mate.altmate.service.ClientService;
import com.alt_mate.altmate.service.SocialAccountService;
import com.alt_mate.altmate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class AdCampaignController {
    
    private final AdCampaignService adCampaignService;
    private final ClientService clientService;
    private final SocialAccountService socialAccountService;
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AdCampaignDTO>> createCampaign(@Valid @RequestBody AdCampaignCreateRequest request) {
        AdCampaign campaign = mapToEntity(request);
        AdCampaign createdCampaign = adCampaignService.createCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Campaign created successfully", mapToDTO(createdCampaign)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdCampaignDTO>> getCampaignById(@PathVariable Long id) {
        AdCampaign campaign = adCampaignService.getCampaignById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(campaign)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdCampaignDTO>>> getAllCampaigns() {
        List<AdCampaign> campaigns = adCampaignService.getAllCampaigns();
        List<AdCampaignDTO> campaignDTOs = campaigns.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(campaignDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<AdCampaignDTO>>> getCampaignsByClient(@PathVariable Long clientId) {
        List<AdCampaign> campaigns = adCampaignService.getCampaignsByClient(clientId);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(campaignDTOs));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AdCampaignDTO>>> getCampaignsByStatus(@PathVariable CampaignStatus status) {
        List<AdCampaign> campaigns = adCampaignService.getCampaignsByStatus(status);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(campaignDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdCampaignDTO>> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody AdCampaignCreateRequest request) {
        AdCampaign campaignDetails = mapToEntity(request);
        AdCampaign updatedCampaign = adCampaignService.updateCampaign(id, campaignDetails);
        return ResponseEntity.ok(ApiResponse.success("Campaign updated successfully", mapToDTO(updatedCampaign)));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AdCampaignDTO>> updateCampaignStatus(
            @PathVariable Long id,
            @RequestParam CampaignStatus status) {
        AdCampaign campaign = adCampaignService.updateCampaignStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Campaign status updated successfully", mapToDTO(campaign)));
    }
    
    @PutMapping("/{id}/spend")
    public ResponseEntity<ApiResponse<AdCampaignDTO>> updateSpentAmount(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        AdCampaign campaign = adCampaignService.updateSpentAmount(id, amount);
        return ResponseEntity.ok(ApiResponse.success("Spent amount updated successfully", mapToDTO(campaign)));
    }
    
    @PutMapping("/{id}/metrics")
    public ResponseEntity<ApiResponse<AdCampaignDTO>> updateCampaignMetrics(
            @PathVariable Long id,
            @RequestParam Integer impressions,
            @RequestParam Integer clicks,
            @RequestParam Integer conversions) {
        AdCampaign campaign = adCampaignService.updateCampaignMetrics(id, impressions, clicks, conversions);
        return ResponseEntity.ok(ApiResponse.success("Campaign metrics updated successfully", mapToDTO(campaign)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCampaign(@PathVariable Long id) {
        adCampaignService.deleteCampaign(id);
        return ResponseEntity.ok(ApiResponse.success("Campaign deleted successfully", null));
    }
    
    @GetMapping("/client/{clientId}/total-spent")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalSpentByClient(@PathVariable Long clientId) {
        BigDecimal totalSpent = adCampaignService.getTotalSpentByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(totalSpent));
    }
    
    @PostMapping("/auto-pause")
    public ResponseEntity<ApiResponse<String>> autoPauseBudgetExhaustedCampaigns() {
        adCampaignService.autoPauseBudgetExhaustedCampaigns();
        return ResponseEntity.ok(ApiResponse.success("Auto-pause executed successfully", null));
    }
    
    private AdCampaignDTO mapToDTO(AdCampaign campaign) {
        AdCampaignDTO dto = new AdCampaignDTO();
        dto.setId(campaign.getId());
        dto.setCampaignName(campaign.getCampaignName());
        dto.setObjective(campaign.getObjective());
        dto.setTotalBudget(campaign.getTotalBudget());
        dto.setSpentAmount(campaign.getSpentAmount());
        dto.setRemainingBudget(campaign.getRemainingBudget());
        dto.setAutoPauseEnabled(campaign.getAutoPauseEnabled());
        dto.setStatus(campaign.getStatus());
        dto.setStartDate(campaign.getStartDate());
        dto.setEndDate(campaign.getEndDate());
        dto.setImpressions(campaign.getImpressions());
        dto.setClicks(campaign.getClicks());
        dto.setConversions(campaign.getConversions());
        dto.setCostPerClick(campaign.getCostPerClick());
        dto.setCostPerConversion(campaign.getCostPerConversion());
        dto.setCreatedAt(campaign.getCreatedAt());
        dto.setUpdatedAt(campaign.getUpdatedAt());
        
        if (campaign.getClient() != null) {
            dto.setClientId(campaign.getClient().getId());
            dto.setClientName(campaign.getClient().getName());
        }
        
        if (campaign.getSocialAccount() != null) {
            dto.setSocialAccountId(campaign.getSocialAccount().getId());
            dto.setSocialAccountName(campaign.getSocialAccount().getAccountUsername());
        }
        
        if (campaign.getManagedBy() != null) {
            dto.setManagedById(campaign.getManagedBy().getId());
            dto.setManagedByName(campaign.getManagedBy().getFullname());
        }
        
        return dto;
    }
    
    private AdCampaign mapToEntity(AdCampaignCreateRequest request) {
        AdCampaign campaign = new AdCampaign();
        campaign.setCampaignName(request.getCampaignName());
        campaign.setObjective(request.getObjective());
        campaign.setTotalBudget(request.getTotalBudget());
        campaign.setAutoPauseEnabled(request.getAutoPauseEnabled());
        campaign.setStatus(request.getStatus());
        campaign.setStartDate(request.getStartDate());
        campaign.setEndDate(request.getEndDate());
        
        // Set Client relationship
        if (request.getClientId() != null) {
            Client client = clientService.getClientById(request.getClientId());
            campaign.setClient(client);
        }
        
        // Set SocialAccount relationship
        if (request.getSocialAccountId() != null) {
            SocialAccount account = socialAccountService.getSocialAccountById(request.getSocialAccountId());
            campaign.setSocialAccount(account);
        }
        
        // Set ManagedBy relationship
        if (request.getManagedById() != null) {
            User managedBy = userService.getUserById(request.getManagedById());
            campaign.setManagedBy(managedBy);
        }
        
        return campaign;
    }
}
