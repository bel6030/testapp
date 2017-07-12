package com.codetest.eb.service.impl;

import com.codetest.eb.domain.FileMetadata;
import com.codetest.eb.repository.FileMetadataRepository;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.service.mapper.FileMetadataMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileMetadataServiceImplTest {

    @Mock
    private FileMetadataRepository mockFileMetadataRepository;

    @Mock
    private FileMetadataMapper mockFileMetadataMapper;

    @InjectMocks
    private FileMetadataServiceImpl fileMetadataService;

    @Test
    public void getFileMetadata_shouldReturnFileMetadata() throws Exception {
        when(mockFileMetadataRepository.findOne(any(UUID.class))).thenReturn(mock(FileMetadata.class));
        when(mockFileMetadataMapper.fileMetadataToFileMetadataDTO(any(FileMetadata.class))).thenReturn(mock(FileMetadataDTO.class));

        FileMetadataDTO fileMetadataDTO = fileMetadataService.getFileMetadata(UUID.randomUUID());
        assertThat(fileMetadataDTO, is(notNullValue()));
    }
}
