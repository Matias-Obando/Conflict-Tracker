package com.example.Conflict.Tracker.repository;

import com.example.Conflict.Tracker.model.Faction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactionRepository extends JpaRepository<Faction, Long> {

    List<Faction> findByConflictId(Long conflictId);
}
