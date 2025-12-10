package com.example.Conflict.Tracker.web;

import com.example.Conflict.Tracker.dto.EventCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.EventResponseDTO;
import com.example.Conflict.Tracker.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventResponseDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EventResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDTO create(@RequestBody @Valid EventCreateUpdateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public EventResponseDTO update(@PathVariable Long id,
                                   @RequestBody @Valid EventCreateUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
