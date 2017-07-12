package com.codetest.eb.service.impl;

import com.codetest.eb.domain.FileMetadata;
import com.codetest.eb.repository.FileMetadataRepository;
import com.codetest.eb.service.StorageService;
import com.codetest.eb.service.StorageServiceException;
import com.codetest.eb.service.UploadFileServiceException;
import com.codetest.eb.service.UploadFileService;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.service.mapper.FileMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@Transactional
public class UploadFileServiceImpl implements UploadFileService {

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final StorageService storageService;

    private final FileMetadataMapper fileMetadataMapper;

    private final FileMetadataRepository fileMetadataRepository;

    @Autowired
    public UploadFileServiceImpl(StorageService storageService, FileMetadataMapper fileMetadataMapper,
                                 FileMetadataRepository fileMetadataRepository) {
        this.storageService = storageService;
        this.fileMetadataMapper = fileMetadataMapper;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @Override
    public FileMetadataDTO uploadFile(InputStream inputStream, FileMetadataDTO fileMetadataDTO) {
        try {
            FileMetadata fileMetadata = fileMetadataMapper.fileMetadataDTOToFileMetadata(fileMetadataDTO);
            fileMetadataRepository.save(fileMetadata);

            storageService.store(inputStream, fileMetadata.getUuid());

            return fileMetadataMapper.fileMetadataToFileMetadataDTO(fileMetadata);
        } catch (StorageServiceException ex) {
            log.error("Error uploading file " + fileMetadataDTO);
            throw new UploadFileServiceException("Error uploading file " + fileMetadataDTO, ex);
        }
    }
}
