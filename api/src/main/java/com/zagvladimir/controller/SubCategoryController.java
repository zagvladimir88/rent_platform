package com.zagvladimir.controller;


import com.zagvladimir.controller.mappers.SubItemTypeMapper;
import com.zagvladimir.controller.requests.sub_category.SubCategoryCreateRequest;
import com.zagvladimir.controller.response.SubCategoryResponse;
import com.zagvladimir.domain.SubCategory;
import com.zagvladimir.service.sub_category.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Tag(name = "Sub item types controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sub-item-types")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final SubItemTypeMapper subItemTypeMapper;

    @Operation(summary = "Gets all SubItemType")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the SubItemType",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = SubCategoryResponse.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllISubItemTypes() {
        return new ResponseEntity<>(Collections.singletonMap("result", subCategoryService.findAll().stream().map(subItemTypeMapper::toResponse)),
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
                                            array = @ArraySchema(schema = @Schema(implementation = SubCategoryResponse.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findSubItemTypeById(@PathVariable String id) {
        long itemTypeCategoryId = Long.parseLong(id);
        return new ResponseEntity<>(subCategoryService.findById(itemTypeCategoryId).map(subItemTypeMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new)
                , HttpStatus.OK);
    }

    @Operation(
            summary = "Create new SubItemType",
            responses = {
                    @ApiResponse( responseCode = "201", description = "SubItemType create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryResponse.class))),
                    @ApiResponse( responseCode = "409", description = "SubItemType not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "SubItemType not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createSubItemType(@RequestBody SubCategoryCreateRequest subCategoryCreateRequest) {
        SubCategory subCategory = subItemTypeMapper.convertCreateRequest(subCategoryCreateRequest);
        Long categoryId = subCategoryCreateRequest.getCategoryId();

        subCategoryService.create(subCategory,categoryId);

        return new ResponseEntity<>(subCategoryService.findById(subCategory
                .getId())
                .map(subItemTypeMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new)
                , HttpStatus.CREATED);
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

        subCategoryService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("Sub Item Category was deleted, id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
