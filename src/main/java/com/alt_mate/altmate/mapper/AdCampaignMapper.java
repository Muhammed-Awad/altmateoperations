package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.AdCampaignCreateRequest;
import com.alt_mate.altmate.DTO.AdCampaignDTO;
import com.alt_mate.altmate.model.AdCampaign;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdCampaignMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    @Mapping(target = "socialAccountId", source = "socialAccount.id")
    @Mapping(target = "socialAccountName", source = "socialAccount.accountUsername")
    @Mapping(target = "managedById", source = "managedBy.id")
    @Mapping(target = "managedByName", source = "managedBy.fullname")
    AdCampaignDTO toDTO(AdCampaign campaign);
    
    List<AdCampaignDTO> toDTOList(List<AdCampaign> campaigns);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "socialAccount", ignore = true)
    @Mapping(target = "managedBy", ignore = true)
    @Mapping(target = "spentAmount", ignore = true)
    @Mapping(target = "remainingBudget", ignore = true)
    @Mapping(target = "impressions", ignore = true)
    @Mapping(target = "clicks", ignore = true)
    @Mapping(target = "conversions", ignore = true)
    @Mapping(target = "costPerClick", ignore = true)
    @Mapping(target = "costPerConversion", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AdCampaign toEntity(AdCampaignCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "socialAccount", ignore = true)
    @Mapping(target = "managedBy", ignore = true)
    @Mapping(target = "spentAmount", ignore = true)
    @Mapping(target = "remainingBudget", ignore = true)
    @Mapping(target = "impressions", ignore = true)
    @Mapping(target = "clicks", ignore = true)
    @Mapping(target = "conversions", ignore = true)
    @Mapping(target = "costPerClick", ignore = true)
    @Mapping(target = "costPerConversion", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(AdCampaignCreateRequest request, @MappingTarget AdCampaign campaign);
}
