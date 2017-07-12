package com.codetest.eb.service.dto;

import java.io.InputStream;

public class DownloadFileDTO {

    private InputStream inputStream;

    private String contentType;

    private String fileName;

    private Long contentLength;

    public DownloadFileDTO(InputStream inputStream, FileMetadataDTO fileMetadataDTO) {
        this.inputStream = inputStream;
        this.contentType = fileMetadataDTO.getContentType();
        this.fileName = fileMetadataDTO.getFileName();
        this.contentLength = fileMetadataDTO.getFileSize();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getContentLength() {
        return contentLength;
    }

    @Override
    public String toString() {
        return "DownloadFileDTO{" +
            "inputStream=" + inputStream +
            ", contentType='" + contentType + '\'' +
            ", fileName='" + fileName + '\'' +
            ", contentLength=" + contentLength +
            '}';
    }
}
