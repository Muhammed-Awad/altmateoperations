package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.TaskRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRevisionRepository extends JpaRepository<TaskRevision, Long> {
    
    List<TaskRevision> findByTaskId(Long taskId);
    
    List<TaskRevision> findByRequestedById(Long userId);
    
    @Query("SELECT tr FROM TaskRevision tr WHERE tr.task.id = :taskId ORDER BY tr.revisionNumber DESC")
    List<TaskRevision> findByTaskIdOrderByRevisionNumberDesc(@Param("taskId") Long taskId);
    
    @Query("SELECT COUNT(tr) FROM TaskRevision tr WHERE tr.task.id = :taskId")
    Long countRevisionsByTask(@Param("taskId") Long taskId);
    
    @Query("SELECT tr FROM TaskRevision tr WHERE tr.task.id = :taskId AND tr.revisionNumber = :revisionNumber")
    TaskRevision findByTaskIdAndRevisionNumber(@Param("taskId") Long taskId, 
                                               @Param("revisionNumber") Integer revisionNumber);
}
