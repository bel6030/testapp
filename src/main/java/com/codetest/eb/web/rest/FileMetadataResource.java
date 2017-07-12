package com.codetest.eb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codetest.eb.service.FileMetadataService;
import com.codetest.eb.service.dto.FileMetadataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/filemetadatas")
public class FileMetadataResource {

    private final Logger log = LoggerFactory.getLogger(FileMetadataResource.class);

    private final FileMetadataService fileMetadataService;

    @Autowired
    public FileMetadataResource(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping("/{uuid}")
    @Timed
    public ResponseEntity<FileMetadataDTO> getFileMetadata(@PathVariable String uuid) {
        log.debug("REST request to get FileMetadata : {}", uuid);

        return ResponseEntity.ok().body(fileMetadataService.getFileMetadata(UUID.fromString(uuid)));
    }
}
