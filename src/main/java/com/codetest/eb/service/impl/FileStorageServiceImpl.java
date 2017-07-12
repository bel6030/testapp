package com.codetest.eb.service.impl;

import com.codetest.eb.config.ApplicationProperties;
import com.codetest.eb.service.StorageServiceException;
import com.codetest.eb.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final ApplicationProperties applicationProperties;

    @Autowired
    public FileStorageServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void store(InputStream is, UUID uuid) {
        try {
            Files.copy(is, Paths.get(applicationProperties.getUpload().getDirectory(), uuid.toString()));
        } catch (Exception ex) {
            log.error("Error writing file to disk", ex);
            throw new StorageServiceException("Error storing file", ex);
        }
    }

    @Override
    public InputStream retrieve(UUID uuid) {
        try {
            File file = new File(applicationProperties.getUpload().getDirectory(), uuid.toString());
            return new FileInputStream(file);
        } catch (Exception ex) {
            log.error("Error retrieving file from disk " + uuid, ex);
            throw new StorageServiceException("Error retrieving file from disk " + uuid, ex);
        }
    }
}
