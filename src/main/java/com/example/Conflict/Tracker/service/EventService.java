package com.example.Conflict.Tracker.service;

import com.example.Conflict.Tracker.dto.EventCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.EventResponseDTO;

import java.util.List;

public interface EventService {

    List<EventResponseDTO> findAll();

    EventResponseDTO findById(Long id);

    EventResponseDTO create(EventCreateUpdateDTO dto);

    EventResponseDTO update(Long id, EventCreateUpdateDTO dto);

    void delete(Long id);
}
