package com.zagvladimir.controller;

import com.zagvladimir.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zagvladimir.dto.ImageParams;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Image controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImagesController {

  private final ImageService imageService;

  @Operation(summary = "Upload image for item")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image upload successfully", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content())})
  @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<Object, Object>> uploadImage(
      @PathVariable String id, @RequestPart("file") MultipartFile file) throws IOException {
    String imageUrl = imageService.uploadFile(ImageParams.builder()
            .itemId(Long.parseLong(id))
            .fileExtension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
            .imageBytes(file.getBytes())
            .build());
    return new ResponseEntity<>(Collections.singletonMap("imageUrl", imageUrl), HttpStatus.CREATED);
  }

  @Operation(summary = "Gets image urls by item ID")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Images successfully found", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema()))}),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
        @ApiResponse(responseCode = "500", content = @Content())
      })
  @GetMapping("/{id}")
  public ResponseEntity<Map<Object, Object>> getImageUrls(@PathVariable String id) {
    Long itemId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("imageUrl", imageService.getUrls(itemId)), HttpStatus.CREATED);
  }
}
