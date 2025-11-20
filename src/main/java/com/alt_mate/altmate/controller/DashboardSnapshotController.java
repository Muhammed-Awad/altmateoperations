package com.example.altmate_operations.controller;

import com.example.altmate_operations.DTO.ApiResponse;
import com.example.altmate_operations.model.DashboardSnapshot;
import com.example.altmate_operations.service.DashboardSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard-snapshots")
@RequiredArgsConstructor
public class DashboardSnapshotController {
    
    private final DashboardSnapshotService dashboardSnapshotService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<DashboardSnapshot>> createSnapshot(@RequestBody DashboardSnapshot snapshot) {
        DashboardSnapshot createdSnapshot = dashboardSnapshotService.createSnapshot(snapshot);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Dashboard snapshot created successfully", createdSnapshot));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DashboardSnapshot>> getSnapshotById(@PathVariable Long id) {
        DashboardSnapshot snapshot = dashboardSnapshotService.getSnapshotById(id);
        return ResponseEntity.ok(ApiResponse.success(snapshot));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<DashboardSnapshot>>> getAllSnapshots() {
        List<DashboardSnapshot> snapshots = dashboardSnapshotService.getAllSnapshots();
        return ResponseEntity.ok(ApiResponse.success(snapshots));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<DashboardSnapshot>>> getSnapshotsByClient(@PathVariable Long clientId) {
        List<DashboardSnapshot> snapshots = dashboardSnapshotService.getSnapshotsByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(snapshots));
    }
    
    @GetMapping("/client/{clientId}/latest")
    public ResponseEntity<ApiResponse<DashboardSnapshot>> getLatestSnapshotByClient(@PathVariable Long clientId) {
        DashboardSnapshot snapshot = dashboardSnapshotService.getLatestSnapshotByClient(clientId)
                .orElseThrow(() -> new RuntimeException("No snapshot found for client: " + clientId));
        return ResponseEntity.ok(ApiResponse.success(snapshot));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DashboardSnapshot>> updateSnapshot(
            @PathVariable Long id,
            @RequestBody DashboardSnapshot snapshotDetails) {
        DashboardSnapshot updatedSnapshot = dashboardSnapshotService.updateSnapshot(id, snapshotDetails);
        return ResponseEntity.ok(ApiResponse.success("Dashboard snapshot updated successfully", updatedSnapshot));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSnapshot(@PathVariable Long id) {
        dashboardSnapshotService.deleteSnapshot(id);
        return ResponseEntity.ok(ApiResponse.success("Dashboard snapshot deleted successfully", null));
    }
}
