package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.DashboardSnapshot;
import com.alt_mate.altmate.repository.DashboardSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardSnapshotService {
    
    private final DashboardSnapshotRepository dashboardSnapshotRepository;
    
    @Transactional
    public DashboardSnapshot createSnapshot(DashboardSnapshot snapshot) {
        snapshot.setSnapshotDate(LocalDate.now());
        return dashboardSnapshotRepository.save(snapshot);
    }
    
    @Transactional
    public DashboardSnapshot updateSnapshot(Long snapshotId, DashboardSnapshot snapshotDetails) {
        DashboardSnapshot snapshot = getSnapshotById(snapshotId);
        snapshot.setTotalPosts(snapshotDetails.getTotalPosts());
        snapshot.setTotalReach(snapshotDetails.getTotalReach());
        snapshot.setTotalEngagement(snapshotDetails.getTotalEngagement());
        snapshot.setTotalFollowers(snapshotDetails.getTotalFollowers());
        snapshot.setActiveComments(snapshotDetails.getActiveComments());
        snapshot.setActiveTasks(snapshotDetails.getActiveTasks());
        snapshot.setRevenueGenerated(snapshotDetails.getRevenueGenerated());
        return dashboardSnapshotRepository.save(snapshot);
    }
    
    @Transactional
    public void deleteSnapshot(Long snapshotId) {
        DashboardSnapshot snapshot = getSnapshotById(snapshotId);
        dashboardSnapshotRepository.delete(snapshot);
    }
    
    public DashboardSnapshot getSnapshotById(Long snapshotId) {
        return dashboardSnapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new RuntimeException("Dashboard snapshot not found with id: " + snapshotId));
    }
    
    public List<DashboardSnapshot> getAllSnapshots() {
        return dashboardSnapshotRepository.findAll();
    }
    
    public List<DashboardSnapshot> getSnapshotsByClient(Long clientId) {
        return dashboardSnapshotRepository.findByClientId(clientId);
    }
    
    public List<DashboardSnapshot> getSnapshotsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return dashboardSnapshotRepository.findBySnapshotDateBetween(startDate, endDate);
    }
    
    public List<DashboardSnapshot> getSnapshotsByClientAndDateRange(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return dashboardSnapshotRepository.findByClientIdAndDateRangeOrderByDateDesc(clientId, startDate, endDate);
    }
    
    public List<DashboardSnapshot> getSnapshotsByClientOrderedByDate(Long clientId) {
        return dashboardSnapshotRepository.findByClientIdOrderByDateDesc(clientId);
    }
    
    public Optional<DashboardSnapshot> getLatestSnapshotByClient(Long clientId) {
        return dashboardSnapshotRepository.findLatestByClientId(clientId);
    }
}
