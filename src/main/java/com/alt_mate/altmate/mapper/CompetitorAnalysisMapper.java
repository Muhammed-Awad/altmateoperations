package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.CompetitorAnalysisDTO;
import com.alt_mate.altmate.model.CompetitorAnalysis;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetitorAnalysisMapper {
    
    CompetitorAnalysisDTO toDTO(CompetitorAnalysis analysis);
    
    List<CompetitorAnalysisDTO> toDTOList(List<CompetitorAnalysis> analyses);
}
