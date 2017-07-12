package com.codetest.eb.web.rest;

import com.codetest.eb.service.UploadFileService;
import com.codetest.eb.service.UploadFileServiceException;
import com.codetest.eb.service.dto.FileMetadataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class UploadFileResource {

    private final Logger log = LoggerFactory.getLogger(UploadFileResource.class);

    private final UploadFileService uploadFileService;

    @Autowired
    public UploadFileResource(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to upload file");

        if (file.isEmpty()) {
            log.error("Uploaded file is empty");
            return ResponseEntity.badRequest().body(null);
        }

        FileMetadataDTO fileMetadataDTO = new FileMetadataDTO();
        fileMetadataDTO.setFileName(file.getOriginalFilename());
        fileMetadataDTO.setContentType(file.getContentType() == null
            ? MediaType.APPLICATION_OCTET_STREAM_VALUE : file.getContentType());
        fileMetadataDTO.setFileSize(file.getSize());

        try {
            FileMetadataDTO uploadedFileMetadataDTO = uploadFileService.uploadFile(file.getInputStream(), fileMetadataDTO);
            return ResponseEntity.ok().body(uploadedFileMetadataDTO);
        } catch (IOException ioe) {
            log.error("Error getting input stream for " + fileMetadataDTO, ioe);
            return ResponseEntity.badRequest().body(null);
        } catch (UploadFileServiceException ufse) {
            log.error("Error uploading file " + fileMetadataDTO, ufse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
