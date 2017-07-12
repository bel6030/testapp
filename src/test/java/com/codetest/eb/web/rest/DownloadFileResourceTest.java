package com.codetest.eb.web.rest;


import com.codetest.eb.service.DownloadFileService;
import com.codetest.eb.service.UploadFileService;
import com.codetest.eb.service.dto.DownloadFileDTO;
import com.codetest.eb.web.rest.errors.ExceptionTranslator;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class DownloadFileResourceTest {

    @Mock
    private DownloadFileService downloadFileService;

    private MockMvc mockMvc;

    private MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();

    private ExceptionTranslator exceptionTranslator = new ExceptionTranslator();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DownloadFileResource resource = new DownloadFileResource(downloadFileService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    public void downloadFile_shouldDownloadFile() throws Exception {
        UUID uuid = UUID.randomUUID();

        DownloadFileDTO mockDownloadFileDTO = mock(DownloadFileDTO.class);
        when(mockDownloadFileDTO.getContentType()).thenReturn("image/png");
        when(mockDownloadFileDTO.getInputStream()).thenReturn(IOUtils.toInputStream("nonsensecontent", "UTF-8"));
        when(downloadFileService.downloadFile(uuid)).thenReturn(mockDownloadFileDTO);

        this.mockMvc.perform(get("/api/v1/file/download/" + uuid)
            .contentType("image/png"))
            .andExpect(status().isOk());
    }
}
