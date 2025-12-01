package com.example.Conflict.Tracker.service.Impl;

import com.example.Conflict.Tracker.model.Conflict;
import com.example.Conflict.Tracker.model.ConflictStatus;
import com.example.Conflict.Tracker.model.Country;
import com.example.Conflict.Tracker.dto.ConflictCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.ConflictResponseDTO;
import com.example.Conflict.Tracker.repository.ConflictRepository;
import com.example.Conflict.Tracker.repository.CountryRepository;
import com.example.Conflict.Tracker.service.ConflictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConflictServiceImpl implements ConflictService {

    private final ConflictRepository conflictRepository;
    private final CountryRepository countryRepository;

    public ConflictServiceImpl(ConflictRepository conflictRepository,
                               CountryRepository countryRepository) {
        this.conflictRepository = conflictRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConflictResponseDTO> findAll(String status) {
        List<Conflict> conflicts;
        if (StringUtils.hasText(status)) {
            ConflictStatus st = ConflictStatus.valueOf(status.toUpperCase());
            conflicts = conflictRepository.findByStatus(st);
        } else {
            conflicts = conflictRepository.findAll();
        }

        return conflicts.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConflictResponseDTO findById(Long id) {
        Conflict conflict = conflictRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conflict not found"));
        return toDTO(conflict);
    }

    @Override
    public ConflictResponseDTO create(ConflictCreateUpdateDTO dto) {
        Conflict conflict = new Conflict();
        applyDtoToEntity(dto, conflict);
        conflict = conflictRepository.save(conflict);
        return toDTO(conflict);
    }

    @Override
    public ConflictResponseDTO update(Long id, ConflictCreateUpdateDTO dto) {
        Conflict conflict = conflictRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conflict not found"));

        applyDtoToEntity(dto, conflict);
        return toDTO(conflict);
    }

    @Override
    public void delete(Long id) {
        if (!conflictRepository.existsById(id)) {
            throw new NoSuchElementException("Conflict not found");
        }
        conflictRepository.deleteById(id);
    }

    // ----- helpers -----

    private void applyDtoToEntity(ConflictCreateUpdateDTO dto, Conflict conflict) {
        conflict.setName(dto.getName());
        conflict.setStartDate(dto.getStartDate());
        conflict.setStatus(dto.getStatus());
        conflict.setDescription(dto.getDescription());

        if (dto.getCountryCodes() != null) {
            Set<Country> countries = dto.getCountryCodes().stream()
                    .map(code -> countryRepository
                            .findByCodeIgnoreCase(code)
                            .orElseThrow(() -> new NoSuchElementException("Country not found: " + code)))
                    .collect(Collectors.toSet());
            conflict.setCountries(countries);
        }
    }

    private ConflictResponseDTO toDTO(Conflict conflict) {
        Set<String> codes = conflict.getCountries().stream()
                .map(c -> c.getCode().toUpperCase())
                .collect(Collectors.toSet());

        return new ConflictResponseDTO(
                conflict.getId(),
                conflict.getName(),
                conflict.getStartDate(),
                conflict.getStatus(),
                conflict.getDescription(),
                codes
        );
    }
}
