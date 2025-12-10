package com.example.Conflict.Tracker.service.Impl;

import com.example.Conflict.Tracker.dto.CountryCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.CountryResponseDTO;
import com.example.Conflict.Tracker.model.Country;
import com.example.Conflict.Tracker.repository.CountryRepository;
import com.example.Conflict.Tracker.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryResponseDTO> findAll() {
        return countryRepository.findAll().stream()
                .map(c -> new CountryResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getCode()
                ))
                .toList();
    }

    @Override
    public CountryResponseDTO findById(Long id) {
        Country c = countryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Country not found"));
        return new CountryResponseDTO(c.getId(), c.getName(), c.getCode());
    }

    @Override
    public CountryResponseDTO create(CountryCreateUpdateDTO dto) {
        Country c = new Country();
        c.setName(dto.getName());
        c.setCode(dto.getCode());
        c = countryRepository.save(c);
        return new CountryResponseDTO(c.getId(), c.getName(), c.getCode());
    }

    @Override
    public CountryResponseDTO update(Long id, CountryCreateUpdateDTO dto) {
        Country c = countryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Country not found"));
        c.setName(dto.getName());
        c.setCode(dto.getCode());
        c = countryRepository.save(c);
        return new CountryResponseDTO(c.getId(), c.getName(), c.getCode());
    }

    @Override
    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new NoSuchElementException("Country not found");
        }
        countryRepository.deleteById(id);
    }
}
