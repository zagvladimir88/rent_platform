package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.SearchRequest;
import com.zagvladimir.controller.requests.location.LocationCreateRequest;
import com.zagvladimir.controller.requests.location.LocationUpdateRequest;
import com.zagvladimir.domain.Location;
import com.zagvladimir.repository.CountryRepository;
import com.zagvladimir.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationRestController {

    private final LocationService locationService;
    private final CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<Object> findAllLocations() {
        return new ResponseEntity<>(
                Collections.singletonMap("Countries", locationService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllLocationsWithParams(
            @ModelAttribute SearchRequest searchRequest) {

        int verifiedLimit = Integer.parseInt(searchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(searchRequest.getOffset());

        List<Location> locationList =
                locationService.findAllLocationsWithParams(verifiedLimit, verifiedOffset);

        Map<String, Object> model = new HashMap<>();
        model.put("Locations", locationList);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findLocationById(@PathVariable String id) {
        long locationId = Long.parseLong(id);
        return new ResponseEntity<>(
                Collections.singletonMap("user", locationService.findById(locationId)), HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLocationById(@PathVariable Long id) {

        locationService.delete(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

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
