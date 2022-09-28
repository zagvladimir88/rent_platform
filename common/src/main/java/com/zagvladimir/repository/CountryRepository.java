package com.zagvladimir.repository;

import com.zagvladimir.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Country findByCountryName(String name);

    @Query(value="SELECT * FROM countries limit :limit offset :offset", nativeQuery = true)
    List<Country> findAllCountriesWithParams( int limit, int offset);
}
