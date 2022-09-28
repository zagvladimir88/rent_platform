package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Location;
import com.zagvladimir.repository.LocationRepository;
import com.zagvladimir.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> findAllLocationsWithParams(int limit, int offset) {
        return locationRepository.findAllLocationsWithParams(limit,offset);
    }

    @Override
    public Location save(Location object) {
        return locationRepository.save(object);
    }

    @Override
    public Optional<Location> findById(Long locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public Location update(Location object) {
        return locationRepository.save(object);
    }

    @Override
    public Long delete(Long id) {
        locationRepository.deleteById(id);
        return id;
    }

}
