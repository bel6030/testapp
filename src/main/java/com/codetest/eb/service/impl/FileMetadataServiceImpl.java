package com.codetest.eb.service.impl;

import com.codetest.eb.domain.FileMetadata;
import com.codetest.eb.repository.FileMetadataRepository;
import com.codetest.eb.service.FileMetadataService;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.service.mapper.FileMetadataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetadataRepository fileMetadataRepository;

    private final FileMetadataMapper fileMetadataMapper;

    @Autowired
    public FileMetadataServiceImpl(FileMetadataRepository fileMetadataRepository, FileMetadataMapper fileMetadataMapper) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.fileMetadataMapper = fileMetadataMapper;
    }

    @Override
    public FileMetadataDTO getFileMetadata(UUID uuid) {
        FileMetadata fileMetadata = fileMetadataRepository.findOne(uuid);
        return fileMetadataMapper.fileMetadataToFileMetadataDTO(fileMetadata);
    }
}
