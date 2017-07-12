package com.codetest.eb.web.rest;

import com.codetest.eb.service.DownloadFileService;
import com.codetest.eb.service.dto.DownloadFileDTO;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/file")
public class DownloadFileResource {

    private final Logger log = LoggerFactory.getLogger(DownloadFileResource.class);

    private final DownloadFileService downloadFileService;

    @Autowired
    public DownloadFileResource(DownloadFileService downloadFileService) {
        this.downloadFileService = downloadFileService;
    }

    @GetMapping("/download/{uuid}")
    public void download(@PathVariable String uuid, HttpServletResponse response) throws Exception {
        log.debug("REST request to download file : {}", uuid);

        DownloadFileDTO downloadFileDTO = downloadFileService.downloadFile(UUID.fromString(uuid));

        response.addHeader("Content-disposition", "attachment;filename=" + downloadFileDTO.getFileName());
        response.setContentType(downloadFileDTO.getContentType());
        response.setContentLength(downloadFileDTO.getContentLength().intValue());

        IOUtils.copy(downloadFileDTO.getInputStream(), response.getOutputStream());
        response.flushBuffer();
    }
}
