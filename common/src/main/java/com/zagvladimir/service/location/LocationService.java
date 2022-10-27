package com.zagvladimir.service.location;

import com.zagvladimir.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

  List<Location> findAll();

  Page<Location> findAll(Pageable page);

  Location create(Location location, Long countryId);

  Location findById(Long locationId);

  Location update(Location location, Long countryId);

  Long delete(Long id);
}
