package com.zagvladimir.service.country;

import com.zagvladimir.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface CountryService {

    Page<Country> findAll(Pageable pageable);
    List<Country> findAll();

    Optional<Country> findCountryById(Long countryId);

    Country create(Country country);

    Long delete(Long countryId);

    Country update(Country countryForUpdate);
}
