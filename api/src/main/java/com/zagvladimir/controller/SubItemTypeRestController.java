package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.sub_item_type.SubItemTypeCreateRequest;
import com.zagvladimir.domain.SubItemType;
import com.zagvladimir.repository.ItemCategoryRepository;
import com.zagvladimir.repository.SubItemTypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sub-item-types")
public class SubItemTypeRestController {

    private final SubItemTypeRepository subItemTypeRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @Operation(summary = "Gets all SubItemType")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the SubItemType",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = SubItemType.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllISubItemTypes() {
        return new ResponseEntity<>(Collections.singletonMap("result", subItemTypeRepository.findAll()),
                HttpStatus.OK);
    }

    @Operation(summary = "Gets SubItemType by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the SubItemType by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = SubItemType.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findSubItemTypeById(@PathVariable String id) {
        long itemTypeCategoryId = Long.parseLong(id);
        return new ResponseEntity<>(Collections.singletonMap("role", subItemTypeRepository.findById(itemTypeCategoryId)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new SubItemType",
            responses = {
                    @ApiResponse( responseCode = "201", description = "SubItemType create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubItemType.class))),
                    @ApiResponse( responseCode = "409", description = "SubItemType not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "SubItemType not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createSubItemType(@RequestBody SubItemTypeCreateRequest subItemTypeCreateRequest) {
        SubItemType subItemType = new SubItemType();

        subItemType.setSubCategoryName(subItemTypeCreateRequest.getSubCategoryName());
        subItemType.setItemCategory(itemCategoryRepository.findById(subItemTypeCreateRequest.getCategoryId()).get());
        subItemType.setCreationDate(new Timestamp(new Date().getTime()));
        subItemType.setModificationDate(subItemType.getCreationDate());
        subItemType.setStatus(subItemTypeCreateRequest.getStatus());

        subItemTypeRepository.save(subItemType);


        Map<String, Object> model = new HashMap<>();
        model.put("Sub_item_types",subItemTypeRepository.findById(subItemType.getId()).get());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete SubItemType",
            description = "This can only be done by the logged in user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "SubItemType deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "SubItemType not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubItemTypeById(@PathVariable Long id){

        subItemTypeRepository.deleteById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("Sub Item Category was deleted, id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
