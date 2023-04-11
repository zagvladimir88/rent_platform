package com.zagvladimir.controller;

import com.zagvladimir.dto.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.dto.response.SubCategoryResponse;
import com.zagvladimir.service.sub_category.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Sub categories controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sub-categories")
public class SubCategoryController {

  private final SubCategoryService subCategoryService;


  @Operation(summary = "Gets all Sub Category")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the sub category",
            content = {
              @Content(
                  mediaType = "application/json",
                  array =
                      @ArraySchema(schema = @Schema(implementation = SubCategoryResponse.class)))
            })
      })
  @GetMapping
  public ResponseEntity<Object> findAllISubCategories(@ParameterObject Pageable pageable) {
    return new ResponseEntity<>( subCategoryService.findAll(pageable), HttpStatus.OK);
  }

  @Operation(summary = "Gets sub category by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the sub category by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array =
                      @ArraySchema(schema = @Schema(implementation = SubCategoryResponse.class)))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<Object> findSubCategoryById(@PathVariable String id) {
    long itemTypeCategoryId = Long.parseLong(id);
    return new ResponseEntity<>(subCategoryService.findById(itemTypeCategoryId), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new sub category",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "sub category create successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubCategoryResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "sub category not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "sub category not created, Illegal Arguments",
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
  public ResponseEntity<Object> createSubCategory(
      @Valid @RequestBody SubCategoryCreateRequest subCategoryCreateRequest) {
    return new ResponseEntity<>(subCategoryService.create(subCategoryCreateRequest),
        HttpStatus.CREATED);
  }
}
