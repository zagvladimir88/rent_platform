package com.zagvladimir.controller;


import com.zagvladimir.controller.mappers.ItemMapper;
import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.controller.response.ItemResponse;
import com.zagvladimir.domain.Item;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.ItemService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Tag(name = "Items controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;


    @Operation(summary = "Gets all items")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the items",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(itemService.findAll().stream().map(itemMapper::toItemResponse),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Gets items with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the items",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class))))
            })
    @GetMapping("/search")
    public ResponseEntity<Object> findAllItemsWithParams(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(itemService.findAll(pageable).map(itemMapper::toItemResponse), HttpStatus.OK);
    }

    @Operation(summary = "Gets item by ID")
    @ApiResponses(
            value = {
                     @ApiResponse(
                            responseCode = "200",
                            description = "Found the item by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item not found",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Item not found",
                            content = @Content(schema = @Schema(implementation = ErrorContainer.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByItemId(@PathVariable Long id) {
    ItemResponse itemResponse = itemMapper.toItemResponse(itemService.findById(id));
        return new ResponseEntity<>(Collections.singletonMap("item",itemResponse), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new Item",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Item create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponse.class))),
                    @ApiResponse( responseCode = "409", description = "Item not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Item not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemCreateRequest createRequest) {

        Item item = itemMapper.itemCreateRequestToItem(createRequest);
        Long subItemTypeId = createRequest.getItemTypeId();
        Long ownerId = createRequest.getOwnerId();
        Long locationId = createRequest.getLocationId();

        itemService.create(item,subItemTypeId,ownerId,locationId);

        return new ResponseEntity<>(itemMapper.toItemResponse(itemService.findById(item.getId())), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete Item",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemsById(@PathVariable Long id){

        itemService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("delete item id = ", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
