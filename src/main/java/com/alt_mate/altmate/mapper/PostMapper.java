package com.example.altmate_operations.mapper;

import com.example.altmate_operations.DTO.PostCreateRequest;
import com.example.altmate_operations.DTO.PostDTO;
import com.example.altmate_operations.model.Post;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    @Mapping(target = "socialAccountId", source = "socialAccount.id")
    @Mapping(target = "socialAccountName", source = "socialAccount.accountUsername")
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "createdByName", source = "createdBy.fullname")
    PostDTO toDTO(Post post);
    
    List<PostDTO> toDTOList(List<Post> posts);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "socialAccount", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "reach", ignore = true)
    @Mapping(target = "impressions", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "shares", ignore = true)
    @Mapping(target = "engagementRate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toEntity(PostCreateRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "socialAccount", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "reach", ignore = true)
    @Mapping(target = "impressions", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "shares", ignore = true)
    @Mapping(target = "engagementRate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(PostCreateRequest request, @MappingTarget Post post);
}
