package com.example.altmate_operations.service;

import com.example.altmate_operations.model.FileMetadata;
import com.example.altmate_operations.model.FileType;
import com.example.altmate_operations.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileMetadataService {
    
    private final FileMetadataRepository fileMetadataRepository;
    
    @Transactional
    public FileMetadata uploadFile(FileMetadata fileMetadata) {
        fileMetadata.setUploadedAt(LocalDateTime.now());
        return fileMetadataRepository.save(fileMetadata);
    }
    
    @Transactional
    public FileMetadata createFileMetadata(FileMetadata fileMetadata) {
        fileMetadata.setUploadedAt(LocalDateTime.now());
        return fileMetadataRepository.save(fileMetadata);
    }
    
    @Transactional
    public void deleteFileMetadata(Long fileId) {
        FileMetadata file = getFileMetadataById(fileId);
        fileMetadataRepository.delete(file);
    }
    
    public List<FileMetadata> getFilesUploadedByUser(Long userId) {
        return fileMetadataRepository.findByUploadedById(userId);
    }
    
    @Transactional
    public FileMetadata updateFileMetadata(Long fileId, FileMetadata fileDetails) {
        FileMetadata file = getFileMetadataById(fileId);
        file.setFileName(fileDetails.getFileName());
        file.setFileType(fileDetails.getFileType());
        file.setDescription(fileDetails.getDescription());
        return fileMetadataRepository.save(file);
    }
    
    @Transactional
    public void deleteFile(Long fileId) {
        FileMetadata file = getFileMetadataById(fileId);
        fileMetadataRepository.delete(file);
    }
    
    public FileMetadata getFileMetadataById(Long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File metadata not found with id: " + fileId));
    }
    
    public Optional<FileMetadata> getFileMetadataByUrl(String fileUrl) {
        return fileMetadataRepository.findByFileUrl(fileUrl);
    }
    
    public List<FileMetadata> getAllFileMetadata() {
        return fileMetadataRepository.findAll();
    }
    
    public List<FileMetadata> getFilesByClient(Long clientId) {
        return fileMetadataRepository.findByClientId(clientId);
    }
    
    public List<FileMetadata> getFilesByType(FileType fileType) {
        return fileMetadataRepository.findByFileType(fileType);
    }
    
    public List<FileMetadata> getFilesByUploader(Long userId) {
        return fileMetadataRepository.findByUploadedById(userId);
    }
    
    public List<FileMetadata> getFilesByClientAndType(Long clientId, FileType fileType) {
        return fileMetadataRepository.findByClientIdAndFileType(clientId, fileType);
    }
    
    public List<FileMetadata> getFilesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return fileMetadataRepository.findByUploadedAtBetween(startDate, endDate);
    }
    
    public List<FileMetadata> searchFilesByName(String fileName) {
        return fileMetadataRepository.searchByFileName(fileName);
    }
    
    public Long countFilesByClient(Long clientId) {
        return fileMetadataRepository.countFilesByClient(clientId);
    }
    
    public Long getTotalFileSizeByClient(Long clientId) {
        return fileMetadataRepository.getTotalFileSizeByClient(clientId);
    }
}
