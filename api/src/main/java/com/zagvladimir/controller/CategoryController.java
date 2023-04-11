package com.zagvladimir.controller;

import com.zagvladimir.mappers.ItemCategoryMapper;
import com.zagvladimir.dto.requests.category.CategoryCreateRequest;
import com.zagvladimir.dto.response.CategoryResponse;
import com.zagvladimir.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Tag(name = "Item Category controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item-categories")
public class CategoryController {

  private final CategoryService categoryService;
  private final ItemCategoryMapper itemCategoryMapper;

  @Operation(
      summary = "Gets all Items Category",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the ItemsCategory",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))
            })
      })
  @GetMapping
  public ResponseEntity<Object> findAllItemCategories(@ParameterObject Pageable pageable) {
      return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets Item Category by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the Item Category by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<Object> findItemCategoryById(@PathVariable Long id) {
    return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new Item Category",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "ItemCategory was created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "ItemCategory not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "ItemCategory not created, Illegal Arguments",
            content = @Content)
      },
          parameters = {
                  @Parameter(
                          in = ParameterIn.HEADER,
                          name = "X-Auth-Token",
                          required = true,
                          description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createItemCategory(
      @RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
    return new ResponseEntity<>(categoryService.create(categoryCreateRequest), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Soft Delete Category",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Item Category status was changed to deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Item Category not found",
            content = @Content)
      })
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> softDeleteItemCategoryById(@PathVariable String id) {
      Long categoryId = Long.parseLong(id);
    categoryService.softDelete(categoryId);
    return new ResponseEntity<>(
        Collections.singletonMap("The category was deleted, id:", categoryId), HttpStatus.OK);
  }
}
