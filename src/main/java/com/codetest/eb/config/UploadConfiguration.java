package com.codetest.eb.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class UploadConfiguration {

    private final ApplicationProperties applicationProperties;

    public UploadConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void createUploadDirectory() {
        if (!Files.exists(Paths.get(applicationProperties.getUpload().getDirectory()))) {
            try {
                Files.createDirectories(Paths.get(applicationProperties.getUpload().getDirectory()));
            } catch (Exception ex) {
                throw new RuntimeException("Could not create upload directory '"
                    + applicationProperties.getUpload().getDirectory() + "', uploads will fail", ex);
            }
        }
    }
}
