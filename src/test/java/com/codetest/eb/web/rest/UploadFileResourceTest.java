package com.codetest.eb.web.rest;

import com.codetest.eb.service.UploadFileService;
import com.codetest.eb.service.UploadFileServiceException;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UploadFileResourceTest {

    @Mock
    private UploadFileService uploadFileService;

    private MockMvc mockMvc;

    private MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();

    private ExceptionTranslator exceptionTranslator = new ExceptionTranslator();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UploadFileResource resource = new UploadFileResource(uploadFileService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    public void uploadFile_shouldUploadFile() throws Exception {
        UUID uuid = UUID.randomUUID();

        FileMetadataDTO fileMetadataDTO = new FileMetadataDTO();
        fileMetadataDTO.setUuid(uuid);
        fileMetadataDTO.setFileName("test.txt");

        when(uploadFileService.uploadFile(any(InputStream.class), any(FileMetadataDTO.class))).thenReturn(fileMetadataDTO);

        MockMultipartFile file = new MockMultipartFile("file", "b.png", "image/png", "nonsensecontent".getBytes());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/v1/file/upload").file(file);
        this.mockMvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid").value(uuid.toString()))
            .andExpect(jsonPath("$.fileName").value("test.txt"));
    }

    @Test
    public void uploadFile_shouldNotUploadEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "b.png", "image/png", "".getBytes());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/v1/file/upload").file(file);
        this.mockMvc.perform(builder)
            .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadFile_shouldNotUploadIfServerSideError() throws Exception {
        when(uploadFileService.uploadFile(any(InputStream.class), any(FileMetadataDTO.class))).thenThrow(UploadFileServiceException.class);

        MockMultipartFile file = new MockMultipartFile("file", "b.png", "image/png", "nonsensecontent".getBytes());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/v1/file/upload").file(file);
        this.mockMvc.perform(builder)
            .andExpect(status().isInternalServerError());
    }
}
