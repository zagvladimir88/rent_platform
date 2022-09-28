package com.zagvladimir.repository;

import com.zagvladimir.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {

    @Query(value="SELECT * FROM locations  limit :limit offset :offset ", nativeQuery = true)
    List<Location> findAllLocationsWithParams(int limit, int offset);

}
