package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.ItemCategoryMapper;
import com.zagvladimir.controller.requests.item_category.ItemCategoryCreateRequest;
import com.zagvladimir.controller.response.ItemCategoryResponse;
import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.service.item_category.ItemCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Item Category controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

  private final ItemCategoryService itemCategoryService;
  private final ItemCategoryMapper itemCategoryMapper;

  @Operation(summary = "Gets all Items Category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the ItemsCategory",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = ItemCategoryResponse.class)))
                          })
          })
  @GetMapping
  public ResponseEntity<Object> findAllItemCategories(@ParameterObject Pageable pageable) {
    return new ResponseEntity<>(
        Collections.singletonMap("result", itemCategoryService.findAll(pageable).stream().map(itemCategoryMapper::toResponse)), HttpStatus.OK);
  }

  @Operation(summary = "Gets Item Category by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the Item Category by id",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = ItemCategoryResponse.class)))
                          })
          })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findItemCategoryById(@PathVariable Long id) {
    return new ResponseEntity<>(
        Collections.singletonMap("itemCategory", itemCategoryService.findById(id).map(itemCategoryMapper::toResponse)),
        HttpStatus.OK);
  }

  @Operation(
          summary = "Create new Item Category",
          responses = {
                  @ApiResponse( responseCode = "201", description = "ItemCategory was created successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ItemCategoryResponse.class))),
                  @ApiResponse( responseCode = "409", description = "ItemCategory not created, Conflict", content = @Content),
                  @ApiResponse( responseCode = "500", description = "ItemCategory not created, Illegal Arguments", content = @Content)
          })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createItemCategory(@RequestBody ItemCategoryCreateRequest itemCategoryCreateRequest) {

    ItemCategory newItemCategory = itemCategoryMapper.convertCreateRequest(itemCategoryCreateRequest);
    itemCategoryService.create(newItemCategory);

    return new ResponseEntity<>(itemCategoryService.findById(newItemCategory.getId()).map(itemCategoryMapper::toResponse), HttpStatus.CREATED);
  }

  @Operation(
          summary = "Delete Item Category",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Item Category was deleted", content = @Content),
                  @ApiResponse(responseCode = "404", description = "Item Category not found", content = @Content)
          })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteItemCategoryById(@PathVariable Long id) {

      itemCategoryService.delete(id);

    Map<String, Object> model = new HashMap<>();
    model.put("role has been deleted id:", id);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }
}
