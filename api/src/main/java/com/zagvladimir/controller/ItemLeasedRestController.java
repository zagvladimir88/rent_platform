package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.UserService;
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

import java.sql.Timestamp;
import java.util.*;

@Tag(name = "Items Leased controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-leased")
public class ItemLeasedRestController {

    private final ItemLeasedService itemLeasedService;
    private final UserService userService;

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
        return new ResponseEntity<>(Collections.singletonMap("result", itemLeasedService.findAll()),
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
    public ResponseEntity<Object> createItem(@RequestBody ItemLeasedCreateRequest createRequest) {

        ItemLeased itemLeased = new ItemLeased();
        itemLeased.setItemId(createRequest.getItemId());
        itemLeased.setRenter(userService.findById(createRequest.getRenterId()));
        itemLeased.setTimeTo(createRequest.getTimeTo());
        itemLeased.setTimeFrom(createRequest.getTimeFrom());
        itemLeased.setPricePerHour(createRequest.getPricePerHour());
        itemLeased.setDiscount(createRequest.getDiscount());
        itemLeased.setFee(createRequest.getFee());
        itemLeased.setPriceTotal(createRequest.getPriceTotal());
        itemLeased.setRenterGradeDescription(createRequest.getRenterGradeDescription());
        itemLeased.setRentierGradeDescription(createRequest.getRentierGradeDescription());
        itemLeased.setCreationDate(new Timestamp(new Date().getTime()));
        itemLeased.setModificationDate(new Timestamp(new Date().getTime()));
        itemLeased.setStatus(createRequest.getStatus());

        itemLeasedService.create(itemLeased);

        List<ItemLeased> items = itemLeasedService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
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
    public ResponseEntity<Map<String, Object>> findItemLeasedById(@PathVariable String id) {

        long itemId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("item", itemLeasedService.findById(itemId)), HttpStatus.OK);
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
