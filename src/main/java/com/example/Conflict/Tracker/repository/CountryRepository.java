package com.example.Conflict.Tracker.repository;

import com.example.Conflict.Tracker.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCodeIgnoreCase(String code);
}
