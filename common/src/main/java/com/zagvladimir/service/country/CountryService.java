package com.zagvladimir.service.country;

import com.zagvladimir.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

  Page<Country> findAll(Pageable pageable);

  List<Country> findAll();

  Country findCountryById(Long countryId);

  Country create(Country country);

  Long delete(Long countryId);

  Country update(Country countryForUpdate);
}
