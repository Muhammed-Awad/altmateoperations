package com.alt_mate.altmate.mapper;

import com.alt_mate.altmate.DTO.FileMetadataDTO;
import com.alt_mate.altmate.model.FileMetadata;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMetadataMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "uploadedBy.id", target = "uploadedById")
    @Mapping(source = "uploadedBy.fullname", target = "uploadedByName")
    FileMetadataDTO toDTO(FileMetadata file);
    
    List<FileMetadataDTO> toDTOList(List<FileMetadata> files);
}
