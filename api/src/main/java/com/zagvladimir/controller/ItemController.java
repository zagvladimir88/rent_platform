package com.zagvladimir.controller;


import com.zagvladimir.controller.mappers.ItemMapper;
import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.controller.response.ItemResponse;
import com.zagvladimir.domain.Item;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
    @GetMapping
    public ResponseEntity<Object> findAllItems(@ParameterObject Pageable page) {
        Page<ItemResponse> itemResponse =  itemService.findAll(page).map(itemMapper::toResponse);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
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
    ItemResponse itemResponse = itemMapper.toResponse(itemService.findById(id));
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
    public ResponseEntity<Object> createItem(
            @RequestBody @Valid ItemCreateRequest createRequest) {

        Item item = itemMapper.convertCreateRequest(createRequest);
        Long subCategoryId = createRequest.getSubCategoryId();
        ItemResponse itemResponse = itemMapper.toResponse(itemService.create(item,subCategoryId));

        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
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
        return new ResponseEntity<>(Collections.singletonMap("The item was deleted, id:",id), HttpStatus.OK);
    }
}
