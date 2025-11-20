package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.ApiResponse;
import com.alt_mate.altmate.model.FileMetadata;
import com.alt_mate.altmate.model.FileType;
import com.alt_mate.altmate.service.FileMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileMetadataController {
    
    private final FileMetadataService fileMetadataService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<FileMetadata>> createFileMetadata(@RequestBody FileMetadata fileMetadata) {
        FileMetadata createdFile = fileMetadataService.createFileMetadata(fileMetadata);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("File metadata created successfully", createdFile));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FileMetadata>> getFileMetadataById(@PathVariable Long id) {
        FileMetadata file = fileMetadataService.getFileMetadataById(id);
        return ResponseEntity.ok(ApiResponse.success(file));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<FileMetadata>>> getAllFileMetadata() {
        List<FileMetadata> files = fileMetadataService.getAllFileMetadata();
        return ResponseEntity.ok(ApiResponse.success(files));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<FileMetadata>>> getFilesByClient(@PathVariable Long clientId) {
        List<FileMetadata> files = fileMetadataService.getFilesByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(files));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<FileMetadata>>> getFilesByType(@PathVariable FileType type) {
        List<FileMetadata> files = fileMetadataService.getFilesByType(type);
        return ResponseEntity.ok(ApiResponse.success(files));
    }
    
    @GetMapping("/uploaded-by/{userId}")
    public ResponseEntity<ApiResponse<List<FileMetadata>>> getFilesUploadedByUser(@PathVariable Long userId) {
        List<FileMetadata> files = fileMetadataService.getFilesUploadedByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(files));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FileMetadata>> updateFileMetadata(
            @PathVariable Long id,
            @RequestBody FileMetadata fileDetails) {
        FileMetadata updatedFile = fileMetadataService.updateFileMetadata(id, fileDetails);
        return ResponseEntity.ok(ApiResponse.success("File metadata updated successfully", updatedFile));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFileMetadata(@PathVariable Long id) {
        fileMetadataService.deleteFileMetadata(id);
        return ResponseEntity.ok(ApiResponse.success("File metadata deleted successfully", null));
    }
    
    @GetMapping("/client/{clientId}/count")
    public ResponseEntity<ApiResponse<Long>> countFilesByClient(@PathVariable Long clientId) {
        Long count = fileMetadataService.countFilesByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
