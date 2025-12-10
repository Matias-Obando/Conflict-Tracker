package com.example.Conflict.Tracker.web;

import com.example.Conflict.Tracker.dto.FactionCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.FactionResponseDTO;
import com.example.Conflict.Tracker.service.FactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factions")
public class FactionController {

    private final FactionService service;

    public FactionController(FactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<FactionResponseDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public FactionResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FactionResponseDTO create(@RequestBody @Valid FactionCreateUpdateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public FactionResponseDTO update(@PathVariable Long id,
                                     @RequestBody @Valid FactionCreateUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
