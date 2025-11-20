package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.TaskRevisionDTO;
import com.alt_mate.altmate.model.TaskRevision;
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
