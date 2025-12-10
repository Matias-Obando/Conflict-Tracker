package com.example.Conflict.Tracker.dto;

import java.time.LocalDate;

public class EventResponseDTO {

    private Long id;
    private LocalDate eventDate;
    private String location;
    private String description;
    private Long conflictId;

    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id, LocalDate eventDate, String location,
                            String description, Long conflictId) {
        this.id = id;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.conflictId = conflictId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getConflictId() {
        return conflictId;
    }

    public void setConflictId(Long conflictId) {
        this.conflictId = conflictId;
    }
}
