package com.zagvladimir.service;


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ImageServiceImpl {

    public String uploadFile(byte[] imageBytes, Long userId) throws IOException {

    Credentials credentials =
        GoogleCredentials.fromStream(
            new FileInputStream(
                "C:\\java_projects\\rent_platform\\common\\src\\main\\resources\\rent-platform-storage-395e699267fc.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        String imageUUID = UUID.randomUUID().toString() + ".jpg";

        BlobId blobId = BlobId.of("rentimages", imageUUID);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpg").build();
        Blob blob = storage.create(blobInfo, imageBytes);

        return blob.signUrl(5L, TimeUnit.MINUTES).toString();
    }
}
