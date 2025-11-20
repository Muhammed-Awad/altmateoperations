package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.ClientComplaint;
import com.alt_mate.altmate.model.ComplaintStatus;
import com.alt_mate.altmate.service.ClientComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ClientComplaintController {
    
    private final ClientComplaintService clientComplaintService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ClientComplaint>> createComplaint(@RequestBody ClientComplaint complaint) {
        ClientComplaint createdComplaint = clientComplaintService.createComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Complaint created successfully", createdComplaint));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientComplaint>> getComplaintById(@PathVariable Long id) {
        ClientComplaint complaint = clientComplaintService.getComplaintById(id);
        return ResponseEntity.ok(ApiResponse.success(complaint));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientComplaint>>> getAllComplaints() {
        List<ClientComplaint> complaints = clientComplaintService.getAllComplaints();
        return ResponseEntity.ok(ApiResponse.success(complaints));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<ClientComplaint>>> getComplaintsByClient(@PathVariable Long clientId) {
        List<ClientComplaint> complaints = clientComplaintService.getComplaintsByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(complaints));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ClientComplaint>>> getComplaintsByStatus(@PathVariable ComplaintStatus status) {
        List<ClientComplaint> complaints = clientComplaintService.getComplaintsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(complaints));
    }
    
    @GetMapping("/open")
    public ResponseEntity<ApiResponse<List<ClientComplaint>>> getOpenComplaints() {
        List<ClientComplaint> complaints = clientComplaintService.getOpenComplaints();
        return ResponseEntity.ok(ApiResponse.success(complaints));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientComplaint>> updateComplaint(
            @PathVariable Long id,
            @RequestBody ClientComplaint complaintDetails) {
        ClientComplaint updatedComplaint = clientComplaintService.updateComplaint(id, complaintDetails);
        return ResponseEntity.ok(ApiResponse.success("Complaint updated successfully", updatedComplaint));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ClientComplaint>> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam ComplaintStatus status) {
        ClientComplaint complaint = clientComplaintService.updateComplaintStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Complaint status updated successfully", complaint));
    }
    
    @PutMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<ClientComplaint>> resolveComplaint(
            @PathVariable Long id,
            @RequestParam String resolution) {
        ClientComplaint complaint = clientComplaintService.resolveComplaint(id, resolution);
        return ResponseEntity.ok(ApiResponse.success("Complaint resolved successfully", complaint));
    }
    
    @PutMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<ClientComplaint>> assignComplaint(
            @PathVariable Long id,
            @RequestParam Long userId) {
        ClientComplaint complaint = clientComplaintService.assignComplaint(id, userId);
        return ResponseEntity.ok(ApiResponse.success("Complaint assigned successfully", complaint));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComplaint(@PathVariable Long id) {
        clientComplaintService.deleteComplaint(id);
        return ResponseEntity.ok(ApiResponse.success("Complaint deleted successfully", null));
    }
    
    @GetMapping("/count/open")
    public ResponseEntity<ApiResponse<Long>> countOpenComplaints() {
        Long count = clientComplaintService.countOpenComplaints();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
