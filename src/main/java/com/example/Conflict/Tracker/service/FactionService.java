package com.example.Conflict.Tracker.service;

import com.example.Conflict.Tracker.dto.FactionCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.FactionResponseDTO;

import java.util.List;

public interface FactionService {

    List<FactionResponseDTO> findAll();

    FactionResponseDTO findById(Long id);

    FactionResponseDTO create(FactionCreateUpdateDTO dto);

    FactionResponseDTO update(Long id, FactionCreateUpdateDTO dto);

    void delete(Long id);
}
