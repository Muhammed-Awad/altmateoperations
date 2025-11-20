package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.CommentCreateRequest;
import com.alt_mate.altmate.DTO.CommentDTO;
import com.alt_mate.altmate.model.Comment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.content", target = "postContent")
    CommentDTO toDTO(Comment comment);
    
    List<CommentDTO> toDTOList(List<Comment> comments);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "commentedAt", ignore = true)
    @Mapping(target = "respondedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Comment toEntity(CommentCreateRequest request);
}
