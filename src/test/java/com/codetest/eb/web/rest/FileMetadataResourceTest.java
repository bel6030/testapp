package com.codetest.eb.web.rest;

import com.codetest.eb.service.FileMetadataService;
import com.codetest.eb.service.dto.FileMetadataDTO;
import com.codetest.eb.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class FileMetadataResourceTest {

    @Mock
    private FileMetadataService fileMetadataService;

    private MockMvc mockMvc;

    private MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();

    private ExceptionTranslator exceptionTranslator = new ExceptionTranslator();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileMetadataResource fileMetadataResource = new FileMetadataResource(fileMetadataService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileMetadataResource)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    public void getFilemetadata_shouldReturnFilemetadata() throws Exception {
        UUID uuid = UUID.randomUUID();

        FileMetadataDTO fileMetadataDTO = new FileMetadataDTO();
        fileMetadataDTO.setUuid(uuid);
        fileMetadataDTO.setFileName("test.txt");

        when(fileMetadataService.getFileMetadata(uuid)).thenReturn(fileMetadataDTO);

        this.mockMvc.perform(get("/api/v1/filemetadatas/" + uuid.toString())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid").value(uuid.toString()))
            .andExpect(jsonPath("$.fileName").value("test.txt"));
    }

}
