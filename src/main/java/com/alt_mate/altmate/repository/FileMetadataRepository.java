package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.FileMetadata;
import com.example.altmate_operations.model.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    
    List<FileMetadata> findByClientId(Long clientId);
    
    List<FileMetadata> findByFileType(FileType fileType);
    
    List<FileMetadata> findByUploadedById(Long userId);
    
    List<FileMetadata> findByClientIdAndFileType(Long clientId, FileType fileType);
    
    Optional<FileMetadata> findByFileUrl(String fileUrl);
    
    @Query("SELECT fm FROM FileMetadata fm WHERE fm.uploadedAt BETWEEN :startDate AND :endDate")
    List<FileMetadata> findByUploadedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT fm FROM FileMetadata fm WHERE fm.fileName LIKE %:fileName%")
    List<FileMetadata> searchByFileName(@Param("fileName") String fileName);
    
    @Query("SELECT COUNT(fm) FROM FileMetadata fm WHERE fm.client.id = :clientId")
    Long countFilesByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT SUM(fm.fileSize) FROM FileMetadata fm WHERE fm.client.id = :clientId")
    Long getTotalFileSizeByClient(@Param("clientId") Long clientId);
}
