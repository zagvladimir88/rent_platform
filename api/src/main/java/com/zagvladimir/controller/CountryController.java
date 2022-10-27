package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.CountryMapper;
import com.zagvladimir.controller.requests.country.CountryCreateRequest;
import com.zagvladimir.controller.requests.country.CountryUpdateRequest;
import com.zagvladimir.controller.response.CountryResponse;
import com.zagvladimir.domain.Country;
import com.zagvladimir.service.country.CountryService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Tag(name = "Country controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

  private final CountryService countryService;
  private final CountryMapper countryMapper;

  @Operation(
      summary = "Gets all countries with pagination",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the countries",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CountryResponse.class))))
      })
  @GetMapping
  public ResponseEntity<Object> findAllCountries(@ParameterObject Pageable pageable) {
    Page<CountryResponse> countryResponse = countryService.findAll(pageable)
            .map(countryMapper::toResponse);
    return new ResponseEntity<>(countryResponse, HttpStatus.OK);
  }

  @Operation(summary = "Gets country by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the country by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CountryResponse.class)))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findCountryById(@PathVariable String id) {

    long userId = Long.parseLong(id);
    CountryResponse countryResponse =countryMapper.toResponse(
            countryService.findCountryById(userId)
    );

    return new ResponseEntity<>(
        Collections.singletonMap("country", countryResponse), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new Country",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Country create successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CountryResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Country not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Country not created, Illegal Arguments",
            content = @Content)
      })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createCountry(
      @RequestBody CountryCreateRequest countryCreateRequest) {

    Country newCountry = countryMapper.toResponse(countryCreateRequest);
    CountryResponse countryResponse = countryMapper.toResponse(countryService.create(newCountry));

    return new ResponseEntity<>(
        Collections.singletonMap("country", countryResponse), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Delete country",
      responses = {
        @ApiResponse(responseCode = "200", description = "country deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "country not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteCountryById(@PathVariable Long id) {

    countryService.delete(id);

    return new ResponseEntity<>(
        Collections.singletonMap("The country was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Update the country",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "country update successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Country.class))),
        @ApiResponse(
            responseCode = "500",
            description = "country not updated, Illegal Arguments",
            content = @Content)
      })
  @Transactional
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateCountry(
      @PathVariable Long id, @RequestBody CountryUpdateRequest countryUpdateRequest) {

    Country country = countryService.findCountryById(id);
    Country countryToUpdate = countryMapper.updateFromRequest(countryUpdateRequest, country);
    CountryResponse countryResponse =
        countryMapper.toResponse(countryService.update(countryToUpdate));

    return new ResponseEntity<>(
        Collections.singletonMap("country", countryResponse), HttpStatus.OK);
  }
}
