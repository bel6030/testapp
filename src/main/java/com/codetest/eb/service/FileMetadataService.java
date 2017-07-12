package com.codetest.eb.service;

import com.codetest.eb.service.dto.FileMetadataDTO;

import java.util.UUID;

public interface FileMetadataService {

    FileMetadataDTO getFileMetadata(UUID uuid);
}
