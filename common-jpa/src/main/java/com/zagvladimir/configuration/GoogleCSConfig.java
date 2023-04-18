package com.zagvladimir.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("gcs.images")
public class GoogleCSConfig {

  private String pathToGCSJsonCredentials;
  private String bucket;

  @Bean
  public Storage getStorage() throws IOException {
    log.info("Reading credentials in GoogleCloudStorageConfig.java was successful");
    return StorageOptions.newBuilder()
            .setCredentials(GoogleCredentials.fromStream(new FileInputStream(pathToGCSJsonCredentials)))
            .build()
            .getService();
  }
}
