package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.UserCreateRequest;
import com.example.altmate_operations.DTO.UserDTO;
import com.example.altmate_operations.DTO.UserUpdateRequest;
import com.example.altmate_operations.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    @Mapping(target = "createdById", source = "createdby.id")
    @Mapping(target = "createdByName", source = "createdby.fullname")
    UserDTO toDTO(User user);
    
    List<UserDTO> toDTOList(List<User> users);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdby", ignore = true)
    User toEntity(UserCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdby", ignore = true)
    void updateEntityFromDTO(UserUpdateRequest request, @MappingTarget User user);
}
