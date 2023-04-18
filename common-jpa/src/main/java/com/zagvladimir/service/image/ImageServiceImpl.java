package com.zagvladimir.service.image;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.zagvladimir.configuration.GoogleCSConfig;
import com.zagvladimir.domain.Image;
import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.ImageRepository;
import com.zagvladimir.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import zagvladimir.dto.ImageParams;

import javax.persistence.EntityNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Storage googleCloudStorageService;

    private final GoogleCSConfig googleCSConfig;

    private final ItemRepository itemRepository;

    private final ImageRepository imageRepository;

    @Override
    public String uploadFile(ImageParams imageParams) {
        String imageNameUUID = generateImageName(imageParams.getFileExtension());
        Blob blob = createBlob(imageNameUUID, imageParams.getImageBytes());
        createLinkToImageInDB(imageNameUUID, imageParams.getItemId());
        return blob.signUrl(5L, TimeUnit.MINUTES).toString();
    }

    @Override
    public List<URL> getUrls(Long itemId) {
        return imageRepository.findImageByItemsId(itemId).stream()
                .map(image -> googleCloudStorageService.signUrl(
                        BlobInfo.newBuilder(googleCSConfig.getBucket(), image.getLink()).build(),
                        5, TimeUnit.MINUTES))
                .collect(Collectors.toList());
    }

    private String generateImageName(String fileExtension) {
        return String.format("%S.%s", UUID.randomUUID(), fileExtension);
    }

    private Blob createBlob(String imageName, byte[] imageBytes) {
        BlobId blobId = BlobId.of(googleCSConfig.getBucket(), imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .build();
        return googleCloudStorageService.create(blobInfo, imageBytes);
    }

    private void createLinkToImageInDB(String imageLink, Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()) {
            Image newImage = new Image();
            newImage.setLink(imageLink);
            newImage.setItems(item.get());
            imageRepository.save(newImage);
        } else throw new EntityNotFoundException(String.format("item with id:%d not found", itemId));
    }
}
