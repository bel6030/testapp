package com.codetest.eb.service;

import com.codetest.eb.service.dto.DownloadFileDTO;

import java.util.UUID;

public interface DownloadFileService {

    DownloadFileDTO downloadFile(UUID uuid);
}
