package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.CompetitorAnalysisDTO;
import com.example.altmate_operations.model.CompetitorAnalysis;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetitorAnalysisMapper {
    
    CompetitorAnalysisDTO toDTO(CompetitorAnalysis analysis);
    
    List<CompetitorAnalysisDTO> toDTOList(List<CompetitorAnalysis> analyses);
}
