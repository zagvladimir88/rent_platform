package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.country.CountryCreateRequest;
import com.zagvladimir.controller.requests.country.CountryUpdateRequest;
import com.zagvladimir.domain.Country;
import com.zagvladimir.repository.CountryRepository;
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
@RequestMapping("/api/countries")
public class CountryRestController {

  private final CountryRepository countryRepository;

  @Operation(summary = "Gets all Countries")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the Countries",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = Country.class)))
                          })
          })
  @GetMapping
  public ResponseEntity<Object> findAllCountries() {
    return new ResponseEntity<>(
        Collections.singletonMap("Countries", countryRepository.findAll()), HttpStatus.OK);
  }

  @Operation(
          summary = "Gets all countries with pagination",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the countries",
                          content =
                          @Content(
                                  mediaType = "application/json",
                                  array = @ArraySchema(schema = @Schema(implementation = Country.class))))
          })
  @GetMapping("/search")
  public ResponseEntity<Object> findAllCountriesWithParams(
          @ParameterObject Pageable pageable) {
    return new ResponseEntity<>(countryRepository.findAll(pageable), HttpStatus.OK);
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
                                          array = @ArraySchema(schema = @Schema(implementation = Country.class)))
                          })
          })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findCountryById(@PathVariable String id) {
    long userId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("user", countryRepository.findById(userId)), HttpStatus.OK);
  }

//  @GetMapping("/country/{name}")
//  public ResponseEntity<Map<String, Object>> findByCountryName(@PathVariable String name) {
//    return new ResponseEntity<>(
//        Collections.singletonMap("user", countryRepository.findByCountryName(name)), HttpStatus.OK);
//  }

  @Operation(
          summary = "Create new Country",
          responses = {
                  @ApiResponse( responseCode = "201", description = "Country create successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
                  @ApiResponse( responseCode = "409", description = "Country not created, Conflict", content = @Content),
                  @ApiResponse( responseCode = "500", description = "Country not created, Illegal Arguments", content = @Content)
          })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createCountry(@RequestBody CountryCreateRequest countryCreateRequest) {

    Country newCountry = new Country();
    newCountry.setCountryName(countryCreateRequest.getCountryName());
    newCountry.setCreationDate(new Timestamp(new Date().getTime()));
    newCountry.setModificationDate(new Timestamp(new Date().getTime()));
    newCountry.setStatus(countryCreateRequest.getStatus());

    countryRepository.save(newCountry);

    List<Country> countries = countryRepository.findAll();

    Map<String, Object> model = new HashMap<>();
    model.put("Country", newCountry.getCountryName());
    model.put("Countries", countries);

    return new ResponseEntity<>(model, HttpStatus.CREATED);
  }

  @Operation(
          summary = "Delete country",
          responses = {
                  @ApiResponse(responseCode = "200", description = "country deleted", content = @Content),
                  @ApiResponse(responseCode = "404", description = "country not found", content = @Content)
          })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteCountryById(@PathVariable Long id) {

    countryRepository.deleteById(id);

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
    Country updatedCountry = countryRepository.findById(id).get();

    updatedCountry.setCountryName(countryUpdateRequest.getCountryName());
    updatedCountry.setModificationDate(new Timestamp(new Date().getTime()));
    updatedCountry.setStatus(countryUpdateRequest.getStatus());

    countryRepository.save(updatedCountry);

    return new ResponseEntity<>("Updated user with ID: " + id, HttpStatus.OK);
  }
}
