package com.example.altmate_operations.service;

import com.example.altmate_operations.model.CompetitorAnalysis;
import com.example.altmate_operations.repository.CompetitorAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitorAnalysisService {
    
    private final CompetitorAnalysisRepository competitorAnalysisRepository;
    
    @Transactional
    public CompetitorAnalysis createAnalysis(CompetitorAnalysis analysis) {
        analysis.setAnalyzedAt(LocalDateTime.now());
        return competitorAnalysisRepository.save(analysis);
    }
    
    @Transactional
    public CompetitorAnalysis updateAnalysis(Long analysisId, CompetitorAnalysis analysisDetails) {
        CompetitorAnalysis analysis = getAnalysisById(analysisId);
        analysis.setCompetitorName(analysisDetails.getCompetitorName());
        analysis.setStrengths(analysisDetails.getStrengths());
        analysis.setWeaknesses(analysisDetails.getWeaknesses());
        analysis.setContentStrategy(analysisDetails.getContentStrategy());
        analysis.setRecommendations(analysisDetails.getRecommendations());
        return competitorAnalysisRepository.save(analysis);
    }
    
    @Transactional
    public void deleteAnalysis(Long analysisId) {
        CompetitorAnalysis analysis = getAnalysisById(analysisId);
        competitorAnalysisRepository.delete(analysis);
    }
    
    public CompetitorAnalysis getAnalysisById(Long analysisId) {
        return competitorAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Competitor analysis not found with id: " + analysisId));
    }
    
    public List<CompetitorAnalysis> getAllAnalyses() {
        return competitorAnalysisRepository.findAll();
    }
    
    public List<CompetitorAnalysis> getAnalysesByClient(Long clientId) {
        return competitorAnalysisRepository.findByClientId(clientId);
    }
    
    public CompetitorAnalysis getLatestAnalysisByClient(Long clientId) {
        List<CompetitorAnalysis> analyses = competitorAnalysisRepository.findByClientIdOrderByAnalyzedAtDesc(clientId);
        if (analyses.isEmpty()) {
            throw new RuntimeException("No competitor analysis found for client: " + clientId);
        }
        return analyses.get(0);
    }
    
    public List<CompetitorAnalysis> getAnalysesByCompetitorName(String competitorName) {
        return competitorAnalysisRepository.findByCompetitorName(competitorName);
    }
    
    public List<CompetitorAnalysis> getAnalysesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return competitorAnalysisRepository.findByAnalyzedAtBetween(startDate, endDate);
    }
    
    public List<CompetitorAnalysis> getAnalysesByClientOrderedByDate(Long clientId) {
        return competitorAnalysisRepository.findByClientIdOrderByAnalyzedAtDesc(clientId);
    }
    
    public List<CompetitorAnalysis> getAnalysesByClientAndCompetitor(Long clientId, String competitorName) {
        return competitorAnalysisRepository.findByClientIdAndCompetitorName(clientId, competitorName);
    }
}
