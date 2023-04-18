package com.zagvladimir.service.image;

import zagvladimir.dto.ImageParams;

import java.net.URL;
import java.util.List;

public interface ImageService {

    String uploadFile(ImageParams imageParams);

    List<URL> getUrls(Long itemId);
}
