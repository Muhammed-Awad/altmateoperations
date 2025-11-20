package com.example.altmate_operations.service;

import com.example.altmate_operations.model.ClientComplaint;
import com.example.altmate_operations.model.ComplaintStatus;
import com.example.altmate_operations.repository.ClientComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientComplaintService {
    
    private final ClientComplaintRepository clientComplaintRepository;
    
    @Transactional
    public ClientComplaint createComplaint(ClientComplaint complaint) {
        complaint.setSubmittedAt(LocalDateTime.now());
        return clientComplaintRepository.save(complaint);
    }
    
    @Transactional
    public ClientComplaint updateComplaint(Long complaintId, ClientComplaint complaintDetails) {
        ClientComplaint complaint = getComplaintById(complaintId);
        complaint.setSubject(complaintDetails.getSubject());
        complaint.setDescription(complaintDetails.getDescription());
        return clientComplaintRepository.save(complaint);
    }
    
    @Transactional
    public ClientComplaint updateComplaintStatus(Long complaintId, ComplaintStatus status) {
        ClientComplaint complaint = getComplaintById(complaintId);
        complaint.setStatus(status);
        
        if (status == ComplaintStatus.RESOLVED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        
        return clientComplaintRepository.save(complaint);
    }
    
    @Transactional
    public ClientComplaint resolveComplaint(Long complaintId, Long userId, String resolution) {
        ClientComplaint complaint = getComplaintById(complaintId);
        complaint.setStatus(ComplaintStatus.RESOLVED);
        complaint.setResolution(resolution);
        complaint.setResolvedAt(LocalDateTime.now());
        // Set resolvedBy using UserService
        return clientComplaintRepository.save(complaint);
    }
    
    @Transactional
    public ClientComplaint resolveComplaint(Long complaintId, String resolution) {
        ClientComplaint complaint = getComplaintById(complaintId);
        complaint.setStatus(ComplaintStatus.RESOLVED);
        complaint.setResolution(resolution);
        complaint.setResolvedAt(LocalDateTime.now());
        return clientComplaintRepository.save(complaint);
    }
    
    @Transactional
    public ClientComplaint assignComplaint(Long complaintId, Long userId) {
        ClientComplaint complaint = getComplaintById(complaintId);
        // Logic to assign complaint to user would go here
        // complaint.setAssignedTo(user);
        return clientComplaintRepository.save(complaint);
    }
    
    public List<ClientComplaint> getOpenComplaints() {
        return clientComplaintRepository.findByStatus(ComplaintStatus.PENDING);
    }
    
    @Transactional
    public void deleteComplaint(Long complaintId) {
        ClientComplaint complaint = getComplaintById(complaintId);
        clientComplaintRepository.delete(complaint);
    }
    
    public ClientComplaint getComplaintById(Long complaintId) {
        return clientComplaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
    }
    
    public List<ClientComplaint> getAllComplaints() {
        return clientComplaintRepository.findAll();
    }
    
    public List<ClientComplaint> getComplaintsByClient(Long clientId) {
        return clientComplaintRepository.findByClientId(clientId);
    }
    
    public List<ClientComplaint> getComplaintsByStatus(ComplaintStatus status) {
        return clientComplaintRepository.findByStatus(status);
    }
    
    public List<ClientComplaint> getComplaintsByResolver(Long userId) {
        return clientComplaintRepository.findByResolvedById(userId);
    }
    
    public List<ClientComplaint> getComplaintsByClientAndStatus(Long clientId, ComplaintStatus status) {
        return clientComplaintRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public List<ClientComplaint> getComplaintsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return clientComplaintRepository.findBySubmittedAtBetween(startDate, endDate);
    }
    
    public List<ClientComplaint> getOpenComplaintsOrderedByDate() {
        return clientComplaintRepository.findOpenComplaintsOrderByDate();
    }
    
    public Long countOpenComplaints() {
        return clientComplaintRepository.countOpenComplaints();
    }
    
    public Long countOpenComplaintsByClient(Long clientId) {
        return clientComplaintRepository.countOpenComplaintsByClient(clientId);
    }
}
