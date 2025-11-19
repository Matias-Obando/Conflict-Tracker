package com.example.Conflict.Tracker.dto;
import com.example.Conflict.Tracker.model.ConflictStatus;
import java.time.LocalDate;
import java.util.Set;

public class ConflictResponseDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private ConflictStatus status;
    private String description;
    private Set<String> countryCodes;

    public ConflictResponseDTO() {
    }

    public ConflictResponseDTO(Long id, String name, LocalDate startDate,
                               ConflictStatus status, String description,
                               Set<String> countryCodes) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
        this.countryCodes = countryCodes;
    }

    // --- gett y sett

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ConflictStatus getStatus() {
        return status;
    }

    public void setStatus(ConflictStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(Set<String> countryCodes) {
        this.countryCodes = countryCodes;
    }
}

