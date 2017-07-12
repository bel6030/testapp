package com.codetest.eb.service.impl;

import com.codetest.eb.service.DownloadFileServiceException;
import com.codetest.eb.service.FileMetadataService;
import com.codetest.eb.service.StorageService;
import com.codetest.eb.service.StorageServiceException;
import com.codetest.eb.service.dto.DownloadFileDTO;
import com.codetest.eb.service.dto.FileMetadataDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DownloadFileServiceImplTest {

    @Mock
    private StorageService mockStorageService;

    @Mock
    private FileMetadataService mockFileMetadataService;

    @InjectMocks
    private DownloadFileServiceImpl downloadFileService;

    @Test(expected = DownloadFileServiceException.class)
    public void downloadFile_shouldThrowExceptionIfErrorRetrievingFile() throws Exception {
        when(mockFileMetadataService.getFileMetadata(any(UUID.class))).thenReturn(new FileMetadataDTO());
        when(mockStorageService.retrieve(any(UUID.class))).thenThrow(StorageServiceException.class);

        downloadFileService.downloadFile(UUID.randomUUID());
    }

    @Test
    public void downloadFile_shouldRetrieveFile() throws Exception {
        when(mockFileMetadataService.getFileMetadata(any(UUID.class))).thenReturn(new FileMetadataDTO());
        when(mockStorageService.retrieve(any(UUID.class))).thenReturn(mock(InputStream.class));

        DownloadFileDTO downloadFileDTO = downloadFileService.downloadFile(UUID.randomUUID());
        assertThat(downloadFileDTO, is(notNullValue()));

        verify(mockFileMetadataService, times(1)).getFileMetadata(any(UUID.class));
        verify(mockStorageService, times(1)).retrieve(any(UUID.class));
    }
}
