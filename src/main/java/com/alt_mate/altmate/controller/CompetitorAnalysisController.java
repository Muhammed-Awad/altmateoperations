package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.CompetitorAnalysis;
import com.alt_mate.altmate.service.CompetitorAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitor-analysis")
@RequiredArgsConstructor
public class CompetitorAnalysisController {
    
    private final CompetitorAnalysisService competitorAnalysisService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CompetitorAnalysis>> createAnalysis(@RequestBody CompetitorAnalysis analysis) {
        CompetitorAnalysis createdAnalysis = competitorAnalysisService.createAnalysis(analysis);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Competitor analysis created successfully", createdAnalysis));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompetitorAnalysis>> getAnalysisById(@PathVariable Long id) {
        CompetitorAnalysis analysis = competitorAnalysisService.getAnalysisById(id);
        return ResponseEntity.ok(ApiResponse.success(analysis));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompetitorAnalysis>>> getAllAnalyses() {
        List<CompetitorAnalysis> analyses = competitorAnalysisService.getAllAnalyses();
        return ResponseEntity.ok(ApiResponse.success(analyses));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<CompetitorAnalysis>>> getAnalysesByClient(@PathVariable Long clientId) {
        List<CompetitorAnalysis> analyses = competitorAnalysisService.getAnalysesByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(analyses));
    }
    
    @GetMapping("/client/{clientId}/latest")
    public ResponseEntity<ApiResponse<CompetitorAnalysis>> getLatestAnalysisByClient(@PathVariable Long clientId) {
        CompetitorAnalysis analysis = competitorAnalysisService.getLatestAnalysisByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(analysis));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompetitorAnalysis>> updateAnalysis(
            @PathVariable Long id,
            @RequestBody CompetitorAnalysis analysisDetails) {
        CompetitorAnalysis updatedAnalysis = competitorAnalysisService.updateAnalysis(id, analysisDetails);
        return ResponseEntity.ok(ApiResponse.success("Competitor analysis updated successfully", updatedAnalysis));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAnalysis(@PathVariable Long id) {
        competitorAnalysisService.deleteAnalysis(id);
        return ResponseEntity.ok(ApiResponse.success("Competitor analysis deleted successfully", null));
    }
}
