package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Country;
import com.zagvladimir.repository.CountryRepository;
import com.zagvladimir.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Page<Country> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }


    @Override
    public Optional<Country> findCountryById(Long countryId) {
        return countryRepository.findById(countryId);
    }

    @Override
    public Country create(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Long delete(Long countryId) {
        countryRepository.deleteById(countryId);
        return countryId;
    }

    @Override
    public Country update(Country countryForUpdate) {
        countryForUpdate.setModificationDate(new Timestamp(new Date().getTime()));
        return countryRepository.save(countryForUpdate);
    }
}
