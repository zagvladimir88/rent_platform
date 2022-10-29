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
  public Storage getStorage() {
    FileInputStream googleJsonCredentials = null;
    try {
      googleJsonCredentials = new FileInputStream(pathToGCSJsonCredentials);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    log.info("Reading credentials in GoogleCloudStorageConfig.java was successful");

    Credentials credentials = null;
    try {
      credentials = GoogleCredentials.fromStream(googleJsonCredentials);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    return storage;
  }
}
