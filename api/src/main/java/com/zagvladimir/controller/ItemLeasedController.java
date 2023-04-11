package com.zagvladimir.controller;

import com.zagvladimir.mappers.ItemLeasedMapper;
import com.zagvladimir.dto.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.dto.response.ItemLeasedResponse;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.item_leased.ItemLeasedService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Items Leased controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-leased")
public class ItemLeasedController {

  private final ItemLeasedService itemLeasedService;
  private final ItemLeasedRepository repository;
  private final ItemLeasedMapper itemLeasedMapper;

  @Operation(
      summary = "Gets all itemsLeased",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the itemsLeased",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))
            })
      })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Object> findAllItemsLeased(@ParameterObject Pageable page) {
    Page<ItemLeasedResponse> itemLeasedResponses =
        itemLeasedService.findAll(page).map(itemLeasedMapper::toResponse);
    return new ResponseEntity<>(itemLeasedResponses, HttpStatus.OK);
  }

  @Operation(
      summary = "Create new itemLeased",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "itemLeased create successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemLeasedResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "itemLeased not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "itemLeased not created, Illegal Arguments",
            content = @Content)
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasAnyRole('USER', 'MANAGER','ADMIN')")
  @PostMapping
  public ResponseEntity<Object> createItemLeased(
      @Valid @RequestBody ItemLeasedCreateRequest createRequest) {
    ItemLeased itemLeased = itemLeasedMapper.convertCreateRequest(createRequest);
    Long renterId = createRequest.getRenterId();
    ItemLeasedResponse itemLeasedResponse =
        itemLeasedMapper.toResponse(itemLeasedService.create(itemLeased, renterId));
    return new ResponseEntity<>(itemLeasedResponse, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Gets itemLeased by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the itemLeased by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))
            })
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(
      "@itemLeasedServiceImpl.getRenterName(#id).equals(principal.username) or hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findItemLeasedById(@PathVariable String id) {
    Long itemLeasedId = Long.parseLong(id);
    ItemLeasedResponse itemLeasedResponse =
        itemLeasedMapper.toResponse(itemLeasedService.findById(itemLeasedId));
    return new ResponseEntity<>(
        Collections.singletonMap("itemLeased", itemLeasedResponse), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets all itemsLeased by renter id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the itemsLeased",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ItemLeasedResponse.class)))
            })
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(
      "@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  @GetMapping("/user/{id}")
  public ResponseEntity<Object> findAllItemsLeasedByUserId(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    List<ItemLeasedResponse> itemLeasedResponses =
        itemLeasedService.findAllByRenterId(userId).stream()
            .map(itemLeasedMapper::toResponse)
            .collect(Collectors.toList());
    return new ResponseEntity<>(itemLeasedResponses, HttpStatus.OK);
  }
}
