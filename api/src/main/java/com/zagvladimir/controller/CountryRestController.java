package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.CountryMapper;
import com.zagvladimir.controller.requests.country.CountryCreateRequest;
import com.zagvladimir.controller.requests.country.CountryUpdateRequest;
import com.zagvladimir.controller.response.CountryResponse;
import com.zagvladimir.domain.Country;
import com.zagvladimir.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Tag(name = "Country controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryRestController {

  private final CountryService countryService;
  private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

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
    return new ResponseEntity<>(countryService.findAll(pageable).map(countryMapper::toResponse), HttpStatus.OK);
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
    return new ResponseEntity<>(
        Collections.singletonMap("country", countryService.findCountryById(userId).map(countryMapper::toResponse)), HttpStatus.OK);
  }

  @Operation(
          summary = "Create new Country",
          responses = {
                  @ApiResponse( responseCode = "201", description = "Country create successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = CountryResponse.class))),
                  @ApiResponse( responseCode = "409", description = "Country not created, Conflict", content = @Content),
                  @ApiResponse( responseCode = "500", description = "Country not created, Illegal Arguments", content = @Content)
          })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createCountry(@RequestBody CountryCreateRequest countryCreateRequest) {
    Country newCountry = countryMapper.countryFromCreateRequest(countryCreateRequest);
    countryService.create(newCountry);
    return new ResponseEntity<>(countryService.findAll().stream().map(countryMapper::toResponse), HttpStatus.CREATED);
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
    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @Operation(
          summary = "Update the country",
          responses = {
                  @ApiResponse( responseCode = "200", description = "country update successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
                  @ApiResponse( responseCode = "500", description = "country not updated, Illegal Arguments", content = @Content)
          })
  @Transactional
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateCountry(
      @PathVariable Long id, @RequestBody CountryUpdateRequest countryUpdateRequest) {
    Country country = countryService.findCountryById(id).orElseThrow(EntityNotFoundException::new);
    Country countryToUpdate = countryMapper.updateFromRequest(countryUpdateRequest,country);

    countryService.update(countryToUpdate);

    return new ResponseEntity<>(countryService.findCountryById(id).map(countryMapper::toResponse), HttpStatus.OK);
  }
}
