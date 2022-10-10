package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.SubItemTypeRepository;
import com.zagvladimir.service.ItemService;
import com.zagvladimir.service.LocationService;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemRestController {

    private final ItemService itemService;
    private final LocationService locationService;
    private final SubItemTypeRepository subItemTypeRepository;
    private final UserService userService;


    @Operation(summary = "Gets all items")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the items",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Item.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(itemService.findAll(),
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
                                    array = @ArraySchema(schema = @Schema(implementation = Item.class))))
            })
    @GetMapping("/search")
    public ResponseEntity<Object> findAllItemsWithParams(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(itemService.findAll(pageable), HttpStatus.OK);
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
                                            array = @ArraySchema(schema = @Schema(implementation = Item.class)))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item not found",
                            content = @Content),
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByItemId(@PathVariable String id) {

        long itemId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("item", itemService.findById(itemId)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new Item",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Item create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
                    @ApiResponse( responseCode = "409", description = "Item not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Item not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createItem(@RequestBody ItemCreateRequest createRequest) {

        Item item = new Item();
        item.setItemName(createRequest.getItemName());
        item.setSubItemType(subItemTypeRepository.findById(createRequest.getItemTypeId()).get());
        item.setLocation(locationService.findById(createRequest.getLocationId()).get());
        item.setItemLocation(createRequest.getItemLocation());
        item.setDescription(createRequest.getDescription());
        item.setOwner(userService.findById(createRequest.getOwnerId()));
        item.setPricePerHour(createRequest.getPricePerHour());
        item.setAvailable(createRequest.getAvailable());
        item.setCreationDate(new Timestamp(new Date().getTime()));
        item.setModificationDate(new Timestamp(new Date().getTime()));
        item.setStatus(createRequest.getStatus());

        itemService.create(item);

        List<Item> items = itemService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("item", item.getItemName());
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
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
