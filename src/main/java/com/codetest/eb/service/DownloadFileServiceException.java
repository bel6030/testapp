package com.codetest.eb.service;

public class DownloadFileServiceException extends RuntimeException {

    public DownloadFileServiceException(String message) {
        super(message);
    }

    public DownloadFileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
