package com.example.Conflict.Tracker.repository;

import com.example.Conflict.Tracker.model.Conflict;
import com.example.Conflict.Tracker.model.ConflictStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConflictRepository extends JpaRepository<Conflict, Long> {

    List<Conflict> findByStatus(ConflictStatus status);


    List<Conflict> findDistinctByCountries_CodeIgnoreCase(String code);
}
