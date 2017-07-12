package com.codetest.eb.service.impl;

import com.codetest.eb.service.*;
import com.codetest.eb.service.dto.DownloadFileDTO;
import com.codetest.eb.service.dto.FileMetadataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class DownloadFileServiceImpl implements DownloadFileService {

    private final Logger log = LoggerFactory.getLogger(DownloadFileServiceImpl.class);

    private final StorageService storageService;

    private final FileMetadataService fileMetadataService;

    @Autowired
    public DownloadFileServiceImpl(StorageService storageService, FileMetadataService fileMetadataService) {
        this.storageService = storageService;
        this.fileMetadataService = fileMetadataService;
    }

    @Override
    public DownloadFileDTO downloadFile(UUID uuid) {
        FileMetadataDTO fileMetadataDTO = fileMetadataService.getFileMetadata(uuid);

        try {
            InputStream inputStream = storageService.retrieve(uuid);
            return new DownloadFileDTO(inputStream, fileMetadataDTO);
        } catch (StorageServiceException ex) {
            log.error("Error downloading file " + fileMetadataDTO);
            throw new DownloadFileServiceException("Error downloading file " + fileMetadataDTO, ex);
        }
    }
}
