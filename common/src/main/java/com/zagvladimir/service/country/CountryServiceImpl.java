package com.zagvladimir.service.country;

import com.zagvladimir.domain.Country;
import com.zagvladimir.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
  public Country findCountryById(Long countryId) {
    return countryRepository
        .findById(countryId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The country with id:%d not found", countryId)));
  }

  @Transactional
  @Override
  public Country create(Country country) {
    return countryRepository.save(country);
  }

  @Transactional
  @Override
  public Long delete(Long countryId) {
    countryRepository.deleteById(countryId);
    return countryId;
  }

  @Transactional
  @Override
  public Country update(Country countryForUpdate) {
    countryForUpdate.setModificationDate(new Timestamp(new Date().getTime()));
    return countryRepository.save(countryForUpdate);
  }
}
