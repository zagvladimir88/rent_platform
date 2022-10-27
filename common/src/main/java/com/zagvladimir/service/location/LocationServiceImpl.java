package com.zagvladimir.service.location;

import com.zagvladimir.domain.Location;
import com.zagvladimir.repository.LocationRepository;
import com.zagvladimir.service.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;
  private final CountryService countryService;

  @Override
  public List<Location> findAll() {
    return locationRepository.findAll();
  }

  @Override
  public Page<Location> findAll(Pageable pageable) {
    return locationRepository.findAll(pageable);
  }

  @Transactional
  @Override
  public Location create(Location location, Long countryId) {
    location.setCountry(countryService.findCountryById(countryId));
    return locationRepository.save(location);
  }

  @Override
  public Location findById(Long locationId) {
    return locationRepository
        .findById(locationId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The location with id:%d not found", locationId)));
  }

  @Transactional
  @Override
  public Location update(Location location, Long countryId) {
    location.setCountry(countryService.findCountryById(countryId));
    return locationRepository.save(location);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    locationRepository.deleteById(id);
    return id;
  }
}
