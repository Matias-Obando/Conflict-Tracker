package com.example.Conflict.Tracker.repository;

import com.example.Conflict.Tracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByConflictId(Long conflictId);
}
