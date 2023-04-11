package com.zagvladimir.controller;

import com.zagvladimir.dto.requests.items.ItemCreateRequest;
import com.zagvladimir.dto.response.ItemResponse;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.item.ItemService;
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

@Tag(name = "Items controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

  private final ItemService itemService;


  @Operation(summary = "Gets items with pagination")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the items", content =
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class))))})
  @GetMapping
  public ResponseEntity<Object> findAllItems(@ParameterObject Pageable page) {
    return new ResponseEntity<>(itemService.findAll(page), HttpStatus.OK);
  }

  @Operation(summary = "Gets item by ID")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the item by id", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))}),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Item not found", content = @Content(schema = @Schema(implementation = ErrorContainer.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity< Object> findByItemId(@PathVariable Long id) {
    return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
  }

  @Operation(summary = "Create new Item")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item create successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponse.class))),
        @ApiResponse(responseCode = "409", description = "Item not created, Conflict", content = @Content),
        @ApiResponse(responseCode = "500", description = "Item not created, Illegal Arguments", content = @Content)})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createItem(@RequestBody @Valid ItemCreateRequest createRequest) {
    return new ResponseEntity<>(itemService.create(createRequest), HttpStatus.CREATED);
  }
}
