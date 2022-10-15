package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.item_category.ItemCategoryCreateRequest;
import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.repository.ItemCategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Tag(name = "Item Category controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item-categories")
public class ItemCategoryRestController {

  private final ItemCategoryRepository itemCategoryRepository;

  @Operation(summary = "Gets all Items Category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the ItemsCategory",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = ItemCategory.class)))
                          })
          })
  @GetMapping
  public ResponseEntity<Object> findAllItemCategories() {
    return new ResponseEntity<>(
        Collections.singletonMap("result", itemCategoryRepository.findAll()), HttpStatus.OK);
  }

  @Operation(summary = "Gets Item Category by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the Item Category by id",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = ItemCategory.class)))
                          })
          })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findItemCategoryById(@PathVariable String id) {
    long itemCategoryId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("item category", itemCategoryRepository.findById(itemCategoryId)),
        HttpStatus.OK);
  }

  @Operation(
          summary = "Create new Item Category",
          responses = {
                  @ApiResponse( responseCode = "201", description = "ItemCategory was created successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ItemCategory.class))),
                  @ApiResponse( responseCode = "409", description = "ItemCategory not created, Conflict", content = @Content),
                  @ApiResponse( responseCode = "500", description = "ItemCategory not created, Illegal Arguments", content = @Content)
          })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createItemCategory(@RequestBody ItemCategoryCreateRequest itemCategoryCreateRequest) {
    ItemCategory newItemCategory = new ItemCategory();

    newItemCategory.setCategoryName(itemCategoryCreateRequest.getCategoryName());
    newItemCategory.setCreationDate(new Timestamp(new Date().getTime()));
    newItemCategory.setModificationDate(new Timestamp(new Date().getTime()));
    newItemCategory.setStatus(itemCategoryCreateRequest.getStatus());

    itemCategoryRepository.save(newItemCategory);

    List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
    Map<String, Object> model = new HashMap<>();
    model.put("item categories", itemCategoryList);

    return new ResponseEntity<>(model, HttpStatus.CREATED);
  }

  @Operation(
          summary = "Delete Item Category",
          description = "This can only be done by the logged in user.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Item Category was deleted", content = @Content),
                  @ApiResponse(responseCode = "404", description = "Item Category not found", content = @Content)
          })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteItemCategoryById(@PathVariable Long id) {

    itemCategoryRepository.deleteById(id);

    Map<String, Object> model = new HashMap<>();
    model.put("role has been deleted id:", id);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }
}
