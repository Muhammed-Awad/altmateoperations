package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.TaskCreateRequest;
import com.example.altmate_operations.DTO.TaskDTO;
import com.example.altmate_operations.model.Task;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "assignedToName", source = "assignedTo.fullname")
    @Mapping(target = "assignedById", source = "assignedBy.id")
    @Mapping(target = "assignedByName", source = "assignedBy.fullname")
    TaskDTO toDTO(Task task);
    
    List<TaskDTO> toDTOList(List<Task> tasks);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    Task toEntity(TaskCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    void updateEntityFromDTO(TaskCreateRequest request, @MappingTarget Task task);
}
