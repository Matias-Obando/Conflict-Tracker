package com.example.Conflict.Tracker.service.Impl;

import com.example.Conflict.Tracker.dto.EventCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.EventResponseDTO;
import com.example.Conflict.Tracker.model.Conflict;
import com.example.Conflict.Tracker.model.Event;
import com.example.Conflict.Tracker.repository.ConflictRepository;
import com.example.Conflict.Tracker.repository.EventRepository;
import com.example.Conflict.Tracker.service.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ConflictRepository conflictRepository;

    public EventServiceImpl(EventRepository eventRepository,
                            ConflictRepository conflictRepository) {
        this.eventRepository = eventRepository;
        this.conflictRepository = conflictRepository;
    }

    @Override
    public List<EventResponseDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public EventResponseDTO findById(Long id) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return toDTO(e);
    }

    @Override
    public EventResponseDTO create(EventCreateUpdateDTO dto) {
        Event e = new Event();
        applyDtoToEntity(dto, e);
        e = eventRepository.save(e);
        return toDTO(e);
    }

    @Override
    public EventResponseDTO update(Long id, EventCreateUpdateDTO dto) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        applyDtoToEntity(dto, e);
        e = eventRepository.save(e);
        return toDTO(e);
    }

    @Override
    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NoSuchElementException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    private void applyDtoToEntity(EventCreateUpdateDTO dto, Event e) {
        e.setEventDate(dto.getEventDate());
        e.setLocation(dto.getLocation());
        e.setDescription(dto.getDescription());

        Conflict conflict = conflictRepository.findById(dto.getConflictId())
                .orElseThrow(() -> new NoSuchElementException("Conflict not found"));
        e.setConflict(conflict);
    }

    private EventResponseDTO toDTO(Event e) {
        return new EventResponseDTO(
                e.getId(),
                e.getEventDate(),
                e.getLocation(),
                e.getDescription(),
                e.getConflict().getId()
        );
    }
}
