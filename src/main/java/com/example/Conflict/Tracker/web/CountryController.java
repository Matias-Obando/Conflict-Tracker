package com.example.Conflict.Tracker.web;

import com.example.Conflict.Tracker.dto.CountryCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.CountryResponseDTO;
import com.example.Conflict.Tracker.dto.ConflictResponseDTO;
import com.example.Conflict.Tracker.service.CountryService;
import com.example.Conflict.Tracker.service.ConflictService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService service;
    private final ConflictService conflictService;

    public CountryController(CountryService service, ConflictService conflictService) {
        this.service = service;
        this.conflictService = conflictService;
    }

    @GetMapping
    public List<CountryResponseDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CountryResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CountryResponseDTO create(@RequestBody @Valid CountryCreateUpdateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CountryResponseDTO update(@PathVariable Long id,
                                     @RequestBody @Valid CountryCreateUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    //  ENDPOINT
    @GetMapping("/{code}/conflicts")
    public List<ConflictResponseDTO> getConflictsByCountry(@PathVariable String code) {
        return conflictService.findByCountryCode(code);
    }
}
