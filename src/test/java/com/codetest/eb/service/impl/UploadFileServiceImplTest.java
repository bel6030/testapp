package com.codetest.eb.service.impl;

import com.codetest.eb.domain.FileMetadata;
import com.codetest.eb.repository.FileMetadataRepository;
import com.codetest.eb.service.StorageService;
import com.codetest.eb.service.StorageServiceException;
import com.codetest.eb.service.UploadFileServiceException;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.service.mapper.FileMetadataMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UploadFileServiceImplTest {

    @Mock
    private StorageService mockStorageService;

    @Mock
    private FileMetadataMapper mockFileMetadataMapper;

    @Mock
    private FileMetadataRepository mockFileMetadataRepository;

    @InjectMocks
    private UploadFileServiceImpl uploadFileService;

    @Test
    public void uploadFile_shouldUploadFile() throws Exception {
        FileMetadata mockFileMetadata = mock(FileMetadata.class);
        when(mockFileMetadataMapper.fileMetadataDTOToFileMetadata(any(FileMetadataDTO.class))).thenReturn(mockFileMetadata);

        uploadFileService.uploadFile(mock(InputStream.class), mock(FileMetadataDTO.class));

        verify(mockFileMetadataRepository, times(1)).save(mockFileMetadata);
        verify(mockStorageService, times(1)).store(any(InputStream.class), any(UUID.class));
        verify(mockFileMetadataMapper, times(1)).fileMetadataToFileMetadataDTO(mockFileMetadata);
    }

    @Test(expected = UploadFileServiceException.class)
    public void uploadFile_sholdThrowUploadFileServiceException() throws Exception {
        when(mockFileMetadataMapper.fileMetadataDTOToFileMetadata(any(FileMetadataDTO.class))).thenReturn(mock(FileMetadata.class));

        doThrow(StorageServiceException.class).when(mockStorageService).store(any(InputStream.class), any(UUID.class));

        uploadFileService.uploadFile(mock(InputStream.class), mock(FileMetadataDTO.class));
    }
}
