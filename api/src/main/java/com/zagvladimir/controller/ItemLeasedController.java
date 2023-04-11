package com.zagvladimir.controller;

import com.zagvladimir.dto.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.dto.response.ItemLeasedResponse;
import com.zagvladimir.service.item_leased.ItemLeasedService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Items Leased controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-leased")
public class ItemLeasedController {

  private final ItemLeasedService itemLeasedService;

  @Operation(summary = "Gets all itemsLeased")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the itemsLeased", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))})})
  @GetMapping
  public ResponseEntity<Object> findAllItemsLeased(@ParameterObject Pageable page) {
    return new ResponseEntity<>(itemLeasedService.findAll(page), HttpStatus.OK);
  }

  @Operation(summary = "Create new itemLeased")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "itemLeased create successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = ItemLeasedResponse.class))),
        @ApiResponse(responseCode = "409", description = "itemLeased not created, Conflict", content = @Content),
        @ApiResponse(responseCode = "500", description = "itemLeased not created, Illegal Arguments", content = @Content)})
  @PostMapping
  public ResponseEntity<Object> createItemLeased(
      @Valid @RequestBody ItemLeasedCreateRequest createRequest) {
    return new ResponseEntity<>(itemLeasedService.create(createRequest), HttpStatus.CREATED);
  }

  @Operation(summary = "Gets itemLeased by ID")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the itemLeased by id", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))})})
  @GetMapping("/{id}")
  public ResponseEntity<Object> findItemLeasedById(@PathVariable String id) {
    Long itemLeasedId = Long.parseLong(id);
    return new ResponseEntity<>(itemLeasedService.findById(itemLeasedId), HttpStatus.OK);
  }

  @Operation(summary = "Gets all itemsLeased by renter id")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the itemsLeased", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))})})
 @GetMapping("/user/{id}")
  public ResponseEntity<Object> findAllItemsLeasedByUserId(@PathVariable String id) {
    Long userId = Long.parseLong(id);

    return new ResponseEntity<>(itemLeasedService.findAllByRenterId(userId), HttpStatus.OK);
  }
}
