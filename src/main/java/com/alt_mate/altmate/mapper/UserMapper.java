package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.UserCreateRequest;
import com.alt_mate.altmate.DTO.UserDTO;
import com.alt_mate.altmate.DTO.UserUpdateRequest;
import com.alt_mate.altmate.model.User;
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
