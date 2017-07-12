package com.codetest.eb.service.mapper;

import com.codetest.eb.domain.FileMetadata;
import com.codetest.eb.service.dto.FileMetadataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {

    FileMetadataDTO fileMetadataToFileMetadataDTO(FileMetadata fileMetadata);

    FileMetadata fileMetadataDTOToFileMetadata(FileMetadataDTO fileMetadataDTO);
}
