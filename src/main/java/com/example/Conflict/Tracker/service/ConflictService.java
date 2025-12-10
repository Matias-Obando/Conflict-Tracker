package com.example.Conflict.Tracker.service;

import com.example.Conflict.Tracker.dto.ConflictCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.ConflictResponseDTO;


import java.util.List;

public interface ConflictService {
    List<ConflictResponseDTO> findAll(String status); //  opcional

    ConflictResponseDTO findById(Long id);

    ConflictResponseDTO create(ConflictCreateUpdateDTO dto);

    ConflictResponseDTO update(Long id, ConflictCreateUpdateDTO dto);
    void delete(Long id);

    List<ConflictResponseDTO> findByCountryCode(String code);

}
