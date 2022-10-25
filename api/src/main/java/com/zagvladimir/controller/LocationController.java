package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.LocationMapper;
import com.zagvladimir.controller.requests.location.LocationCreateRequest;
import com.zagvladimir.controller.requests.location.LocationUpdateRequest;
import com.zagvladimir.controller.response.LocationResponse;
import com.zagvladimir.domain.Location;
import com.zagvladimir.service.location.LocationService;
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

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Tag(name = "Location controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Operation(
            summary = "Gets all locations with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the locations",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LocationResponse.class))))
            })
    @GetMapping
    public ResponseEntity<Object> findAllLocations(
            @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(locationService.findAll(pageable).map(locationMapper::toResponse), HttpStatus.OK);
    }

    @Operation(summary = "Gets location by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the location by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = LocationResponse.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findLocationById(@PathVariable String id) {
        long locationId = Long.parseLong(id);
        return new ResponseEntity<>(
                Collections.singletonMap("Location", locationService.findById(locationId).map(locationMapper::toResponse)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new Location",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Location create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LocationResponse.class))),
                    @ApiResponse( responseCode = "409", description = "Location not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Location not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createLocation(@RequestBody LocationCreateRequest locationCreateRequest) {

        Location newLocation = locationMapper.convertCreateRequest(locationCreateRequest);
        Long countryId = locationCreateRequest.getCountryId();
        locationService.create(newLocation,countryId);

        return new ResponseEntity<>(locationService.findById(newLocation.getId()).map(locationMapper::toResponse), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete Location by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLocationById(@PathVariable Long id) {
        locationService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(
            summary = "Update the Location by id",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Location update successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LocationResponse.class))),
                    @ApiResponse( responseCode = "500", description = "Location not updated, Illegal Arguments", content = @Content)
            })
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable Long id, @RequestBody LocationUpdateRequest locationUpdateRequest) {

        Location locationToUpdate = locationMapper.convertUpdateRequest(
                locationUpdateRequest,
                locationService.findById(id).orElseThrow(EntityNotFoundException::new));

        Long countryId = locationUpdateRequest.getCountryId();
        locationService.update(locationToUpdate, countryId);

        return new ResponseEntity<>(locationService.findById(locationToUpdate.getId()).map(locationMapper::toResponse), HttpStatus.OK);
    }

}
