package com.example.Conflict.Tracker.service.Impl;

import com.example.Conflict.Tracker.dto.FactionCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.FactionResponseDTO;
import com.example.Conflict.Tracker.model.Conflict;
import com.example.Conflict.Tracker.model.Country;
import com.example.Conflict.Tracker.model.Faction;
import com.example.Conflict.Tracker.repository.ConflictRepository;
import com.example.Conflict.Tracker.repository.CountryRepository;
import com.example.Conflict.Tracker.repository.FactionRepository;
import com.example.Conflict.Tracker.service.FactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FactionServiceImpl implements FactionService {

    private final FactionRepository factionRepository;
    private final ConflictRepository conflictRepository;
    private final CountryRepository countryRepository;

    public FactionServiceImpl(FactionRepository factionRepository,
                              ConflictRepository conflictRepository,
                              CountryRepository countryRepository) {
        this.factionRepository = factionRepository;
        this.conflictRepository = conflictRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<FactionResponseDTO> findAll() {
        return factionRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public FactionResponseDTO findById(Long id) {
        Faction f = factionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Faction not found"));
        return toDTO(f);
    }

    @Override
    public FactionResponseDTO create(FactionCreateUpdateDTO dto) {
        Faction f = new Faction();
        applyDtoToEntity(dto, f);
        f = factionRepository.save(f);
        return toDTO(f);
    }

    @Override
    public FactionResponseDTO update(Long id, FactionCreateUpdateDTO dto) {
        Faction f = factionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Faction not found"));
        applyDtoToEntity(dto, f);
        f = factionRepository.save(f);
        return toDTO(f);
    }

    @Override
    public void delete(Long id) {
        if (!factionRepository.existsById(id)) {
            throw new NoSuchElementException("Faction not found");
        }
        factionRepository.deleteById(id);
    }

    private void applyDtoToEntity(FactionCreateUpdateDTO dto, Faction f) {
        f.setName(dto.getName());

        Conflict conflict = conflictRepository.findById(dto.getConflictId())
                .orElseThrow(() -> new NoSuchElementException("Conflict not found"));
        f.setConflict(conflict);

        Set<Country> supporters = dto.getSupporterCountryCodes().stream()
                .map(code -> countryRepository.findByCodeIgnoreCase(code)
                        .orElseThrow(() -> new NoSuchElementException("Country not found: " + code)))
                .collect(Collectors.toSet());

        f.setSupporterCountries(supporters);
    }

    private FactionResponseDTO toDTO(Faction f) {
        Set<String> codes = f.getSupporterCountries().stream()
                .map(c -> c.getCode().toUpperCase())
                .collect(Collectors.toSet());

        return new FactionResponseDTO(
                f.getId(),
                f.getName(),
                f.getConflict().getId(),
                codes
        );
    }
}
