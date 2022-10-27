package com.zagvladimir.controller;

import com.zagvladimir.service.ImageServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Tag(name = "Image controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImagesController {

    private final ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity<Map<Object, Object>> uploadUserPhoto(@RequestBody MultipartFile file) throws IOException {
        Long id = 1L;
        byte[] imageBytes = file.getBytes();
        Long idd = 2L;
        Long ieedd = 2L;
        String imageLink = imageService.uploadFile(imageBytes, id);

        return new ResponseEntity<>(Collections.singletonMap("imageLink", imageLink), HttpStatus.CREATED);
    }
}
