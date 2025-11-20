package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.TaskRevisionDTO;
import com.example.altmate_operations.model.TaskRevision;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskRevisionMapper {
    
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    @Mapping(source = "requestedBy.id", target = "requestedById")
    @Mapping(source = "requestedBy.fullname", target = "requestedByName")
    TaskRevisionDTO toDTO(TaskRevision revision);
    
    List<TaskRevisionDTO> toDTOList(List<TaskRevision> revisions);
}
