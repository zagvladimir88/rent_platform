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

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final Storage storage;

  private final GoogleCSConfig googleCSConfig;

  private final ItemRepository itemRepository;

  private final ImageRepository repository;

  @Override
  public String uploadFile(byte[] imageBytes, Long itemId, String fileExt) {

    String imageNameUUID = String.format("%S.%s", UUID.randomUUID(), fileExt);
    BlobId blobId = BlobId.of(googleCSConfig.getBucket(), imageNameUUID);
    BlobInfo blobInfo =
        BlobInfo.newBuilder(blobId)
            .setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .build();
    Blob blob = storage.create(blobInfo, imageBytes);
    createImageInDB(imageNameUUID, itemId);
    return blob.signUrl(5L, TimeUnit.MINUTES).toString();
  }

  @Override
  public List<URL> getUrls(Long itemId) {
    List<Image> images = repository.findImageByItemsId(itemId);
    return images.stream()
        .map(
            image ->
                storage.signUrl(
                    BlobInfo.newBuilder(googleCSConfig.getBucket(), image.getLink()).build(),
                    5,
                    TimeUnit.MINUTES))
        .collect(Collectors.toList());
  }

  public void createImageInDB(String imageLink, Long itemId) {
    Image newImage = new Image();
    newImage.setLink(imageLink);

    Optional<Item> item = itemRepository.findById(itemId);
    if(item.isPresent()) {
      newImage.setItems(item.get());
    }

    repository.save(newImage);
  }
}
