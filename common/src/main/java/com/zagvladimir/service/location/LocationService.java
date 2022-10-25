package com.zagvladimir.service.location;

import com.zagvladimir.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<Location> findAll();

    Page<Location> findAll(Pageable pageable);

    Location create(Location location, Long countryId);

    Optional<Location> findById(Long locationId);

    Location update(Location location, Long countryId);

    Long delete(Long id);




}