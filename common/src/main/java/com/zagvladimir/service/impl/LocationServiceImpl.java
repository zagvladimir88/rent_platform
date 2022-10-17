package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Location;
import com.zagvladimir.repository.CountryRepository;
import com.zagvladimir.repository.LocationRepository;
import com.zagvladimir.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public List<Location> findAllLocationsWithParams(int limit, int offset) {
        return locationRepository.findAllLocationsWithParams(limit,offset);
    }

    @Override
    public Location create(Location location, Long countryId) {

        location.setCountry(countryRepository.findById(countryId).get());
        location.setCreationDate(new Timestamp(new Date().getTime()));
        location.setModificationDate(location.getCreationDate());

        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> findById(Long locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public Location update(Location location, Long countryId) {
        location.setCountry(countryRepository.findById(countryId).get());
        location.setModificationDate(new Timestamp(new Date().getTime()));
        return locationRepository.save(location);
    }

    @Override
    public Long delete(Long id) {
        locationRepository.deleteById(id);
        return id;
    }

}
