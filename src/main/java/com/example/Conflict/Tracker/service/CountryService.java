package com.example.Conflict.Tracker.service;

import com.example.Conflict.Tracker.dto.CountryCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.CountryResponseDTO;
import java.util.List;

public interface CountryService {
    List<CountryResponseDTO> findAll();

    CountryResponseDTO findById(Long id);

    CountryResponseDTO create(CountryCreateUpdateDTO dto);

    CountryResponseDTO update(Long id, CountryCreateUpdateDTO dto);

    void delete(Long id);
}
