package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.SearchRequest;
import com.zagvladimir.controller.requests.country.CountryCreateRequest;
import com.zagvladimir.controller.requests.country.CountryUpdateRequest;
import com.zagvladimir.domain.Country;
import com.zagvladimir.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/countries")
public class CountryRestController {

  private final CountryRepository countryRepository;

  @GetMapping
  public ResponseEntity<Object> findAllCountries() {
    return new ResponseEntity<>(
        Collections.singletonMap("Countries", countryRepository.findAll()), HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<Object> findAllCountriesWithParams(
      @ModelAttribute SearchRequest searchRequest) {

    int verifiedLimit = Integer.parseInt(searchRequest.getLimit());
    int verifiedOffset = Integer.parseInt(searchRequest.getOffset());

    List<Country> countryList =
        countryRepository.findAllCountriesWithParams(verifiedLimit, verifiedOffset);

    Map<String, Object> model = new HashMap<>();
    model.put("Countries", countryList);

    return new ResponseEntity<>(model, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findCountryById(@PathVariable String id) {
    long userId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("user", countryRepository.findById(userId)), HttpStatus.OK);
  }

  @GetMapping("/login/{name}")
  public ResponseEntity<Map<String, Object>> findByCountryName(@PathVariable String name) {
    return new ResponseEntity<>(
        Collections.singletonMap("user", countryRepository.findByCountryName(name)), HttpStatus.OK);
  }

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

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteCountryById(@PathVariable Long id) {

    countryRepository.deleteById(id);

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

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
