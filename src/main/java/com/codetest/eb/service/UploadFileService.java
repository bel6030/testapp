package com.codetest.eb.service;

import com.codetest.eb.service.dto.FileMetadataDTO;

import java.io.InputStream;

public interface UploadFileService {

    FileMetadataDTO uploadFile(InputStream inputStream, FileMetadataDTO fileMetadataDTO);
}
