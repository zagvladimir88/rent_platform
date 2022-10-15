package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.location.LocationCreateRequest;
import com.zagvladimir.controller.requests.location.LocationUpdateRequest;
import com.zagvladimir.domain.Location;
import com.zagvladimir.repository.CountryRepository;
import com.zagvladimir.service.LocationService;
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

import java.sql.Timestamp;
import java.util.*;

@Tag(name = "Location controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationRestController {

    private final LocationService locationService;
    private final CountryRepository countryRepository;

    @Operation(summary = "Gets all locations")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the locations",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Location.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllLocations() {
        return new ResponseEntity<>(
                Collections.singletonMap("Countries", locationService.findAll()), HttpStatus.OK);
    }

    @Operation(
            summary = "Gets all locations with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the locations",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Location.class))))
            })
    @GetMapping("/search")
    public ResponseEntity<Object> findAllLocationsWithParams(
            @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(locationService.findAll(pageable), HttpStatus.OK);
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
                                            array = @ArraySchema(schema = @Schema(implementation = Location.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findLocationById(@PathVariable String id) {
        long locationId = Long.parseLong(id);
        return new ResponseEntity<>(
                Collections.singletonMap("user", locationService.findById(locationId)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new Location",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Location create successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
                    @ApiResponse( responseCode = "409", description = "Location not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "Location not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createLocation(@RequestBody LocationCreateRequest locationCreateRequest) {

        Location newLocation = new Location();
        newLocation.setPostalCode(locationCreateRequest.getPostalCode());
        newLocation.setName(locationCreateRequest.getName());
        newLocation.setDescription(locationCreateRequest.getDescription());
        newLocation.setCountry(countryRepository.findById(locationCreateRequest.getCountryId()).get());
        newLocation.setCreationDate(new Timestamp(new Date().getTime()));
        newLocation.setModificationDate(new Timestamp(new Date().getTime()));
        newLocation.setStatus(locationCreateRequest.getStatus());

        locationService.save(newLocation);

        Map<String, Object> model = new HashMap<>();

        model.put("Location", locationService.findById(newLocation.getId()));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
                    @ApiResponse( responseCode = "500", description = "Location not updated, Illegal Arguments", content = @Content)
            })
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable Long id, @RequestBody LocationUpdateRequest locationUpdateRequest) {
        Location updatedLocation = locationService.findById(id).get();

        updatedLocation.setPostalCode(locationUpdateRequest.getPostalCode());
        updatedLocation.setName(locationUpdateRequest.getName());
        updatedLocation.setDescription(locationUpdateRequest.getDescription());
        updatedLocation.setCountry(locationUpdateRequest.getCountry());
        updatedLocation.setModificationDate(new Timestamp(new Date().getTime()));
        updatedLocation.setStatus(locationUpdateRequest.getStatus());

        locationService.save(updatedLocation);

        return new ResponseEntity<>("Updated location with ID: " + id, HttpStatus.OK);
    }

}
