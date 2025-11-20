package com.example.altmate_operations.service;

import com.example.altmate_operations.model.TaskRevision;
import com.example.altmate_operations.repository.TaskRevisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskRevisionService {
    
    private final TaskRevisionRepository taskRevisionRepository;
    
    @Transactional
    public TaskRevision createRevision(TaskRevision revision) {
        revision.setRequestedAt(LocalDateTime.now());
        
        // Auto-increment revision number
        Long revisionCount = taskRevisionRepository.countRevisionsByTask(revision.getTask().getId());
        revision.setRevisionNumber(revisionCount.intValue() + 1);
        
        return taskRevisionRepository.save(revision);
    }
    
    @Transactional
    public TaskRevision updateRevision(Long revisionId, TaskRevision revisionDetails) {
        TaskRevision revision = getRevisionById(revisionId);
        revision.setFeedback(revisionDetails.getFeedback());
        revision.setCompletedAt(revisionDetails.getCompletedAt());
        return taskRevisionRepository.save(revision);
    }
    
    @Transactional
    public TaskRevision markRevisionCompleted(Long revisionId) {
        TaskRevision revision = getRevisionById(revisionId);
        revision.setCompletedAt(LocalDateTime.now());
        return taskRevisionRepository.save(revision);
    }
    
    @Transactional
    public void deleteRevision(Long revisionId) {
        TaskRevision revision = getRevisionById(revisionId);
        taskRevisionRepository.delete(revision);
    }
    
    public TaskRevision getRevisionById(Long revisionId) {
        return taskRevisionRepository.findById(revisionId)
                .orElseThrow(() -> new RuntimeException("Task revision not found with id: " + revisionId));
    }
    
    public List<TaskRevision> getAllRevisions() {
        return taskRevisionRepository.findAll();
    }
    
    public List<TaskRevision> getRevisionsByTask(Long taskId) {
        return taskRevisionRepository.findByTaskIdOrderByRevisionNumberDesc(taskId);
    }
    
    public List<TaskRevision> getRevisionsByRequester(Long userId) {
        return taskRevisionRepository.findByRequestedById(userId);
    }
    
    public Long countRevisionsByTask(Long taskId) {
        return taskRevisionRepository.countRevisionsByTask(taskId);
    }
    
    public TaskRevision getRevisionByTaskAndNumber(Long taskId, Integer revisionNumber) {
        return taskRevisionRepository.findByTaskIdAndRevisionNumber(taskId, revisionNumber);
    }
}
