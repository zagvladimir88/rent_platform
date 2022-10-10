package com.zagvladimir.service;

import com.zagvladimir.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<Location> findAll();

    Page<Location> findAll(Pageable pageable);

    List<Location> findAllLocationsWithParams(int limit, int offset);

    Location save(Location object);

    Optional<Location> findById(Long locationId);

    Location update(Location object);

    Long delete(Long id);




}
