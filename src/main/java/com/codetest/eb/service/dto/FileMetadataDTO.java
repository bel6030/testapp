package com.codetest.eb.service.dto;

import javax.persistence.Column;
import java.time.Instant;
import java.util.UUID;

public class FileMetadataDTO {

    private UUID uuid;

    private String fileName;

    private Long fileSize;

    private String contentType;

    private Instant uploadedOn = Instant.now();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Instant getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(Instant uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    @Override
    public String toString() {
        return "FileMetadataDTO{" +
            "uuid=" + uuid +
            ", fileName='" + fileName + '\'' +
            ", fileSize=" + fileSize +
            ", contentType='" + contentType + '\'' +
            ", uploadedOn=" + uploadedOn +
            '}';
    }
}
