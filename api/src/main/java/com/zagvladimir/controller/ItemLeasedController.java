package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.ItemLeasedMapper;
import com.zagvladimir.controller.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.service.item_leased.ItemLeasedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Tag(name = "Items Leased controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-leased")
public class ItemLeasedController {

    private final ItemLeasedService itemLeasedService;
    private final ItemLeasedMapper itemLeasedMapper;

    @Operation(summary = "Gets all itemsLeased",
    responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the itemsLeased",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ItemLeased.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(Collections.singletonMap("result", itemLeasedService.findAll().stream().map(itemLeasedMapper::toResponse)),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Create new itemLeased",
            responses = {
                    @ApiResponse( responseCode = "201", description = "itemLeased create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ItemLeased.class))),
                    @ApiResponse( responseCode = "409", description = "itemLeased not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "itemLeased not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemLeasedCreateRequest createRequest) {
        ItemLeased itemLeased = itemLeasedMapper.convertCreateRequest(createRequest);
        Long renterId = createRequest.getRenterId();
        itemLeasedService.create(itemLeased, renterId);

        return new ResponseEntity<>(itemLeasedMapper.toResponse(itemLeasedService.findById(itemLeased.getId())), HttpStatus.CREATED);
    }

    @Operation(summary = "Gets itemLeased by ID",
                    responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the itemLeased by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ItemLeased.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findItemLeasedById(@PathVariable Long id) {

        return new ResponseEntity<>(Collections.singletonMap("item", itemLeasedMapper.toResponse(itemLeasedService.findById(id))), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete itemLeased",
            responses = {
                    @ApiResponse(responseCode = "200", description = "itemLeased was deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "itemLeased not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemLeasedById(@PathVariable Long id){

        itemLeasedService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
