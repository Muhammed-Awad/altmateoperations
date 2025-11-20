package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.TaskRevision;
import com.alt_mate.altmate.service.TaskRevisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-revisions")
@RequiredArgsConstructor
public class TaskRevisionController {
    
    private final TaskRevisionService taskRevisionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<TaskRevision>> createRevision(@RequestBody TaskRevision revision) {
        TaskRevision createdRevision = taskRevisionService.createRevision(revision);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task revision created successfully", createdRevision));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskRevision>> getRevisionById(@PathVariable Long id) {
        TaskRevision revision = taskRevisionService.getRevisionById(id);
        return ResponseEntity.ok(ApiResponse.success(revision));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskRevision>>> getAllRevisions() {
        List<TaskRevision> revisions = taskRevisionService.getAllRevisions();
        return ResponseEntity.ok(ApiResponse.success(revisions));
    }
    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<TaskRevision>>> getRevisionsByTask(@PathVariable Long taskId) {
        List<TaskRevision> revisions = taskRevisionService.getRevisionsByTask(taskId);
        return ResponseEntity.ok(ApiResponse.success(revisions));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskRevision>> updateRevision(
            @PathVariable Long id,
            @RequestBody TaskRevision revisionDetails) {
        TaskRevision updatedRevision = taskRevisionService.updateRevision(id, revisionDetails);
        return ResponseEntity.ok(ApiResponse.success("Task revision updated successfully", updatedRevision));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRevision(@PathVariable Long id) {
        taskRevisionService.deleteRevision(id);
        return ResponseEntity.ok(ApiResponse.success("Task revision deleted successfully", null));
    }
    
    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<ApiResponse<Long>> countRevisionsByTask(@PathVariable Long taskId) {
        Long count = taskRevisionService.countRevisionsByTask(taskId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
