package com.zagvladimir.service.image;

import java.net.URL;
import java.util.List;

public interface ImageService {

    public String uploadFile(byte[] imageBytes, Long itemId);

    public List<URL> getUrls(Long itemId);
}
