package com.example.Conflict.Tracker.dto;

import com.example.Conflict.Tracker.model.ConflictStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class ConflictCreateUpdateDTO {

    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;

    @NotNull
    private ConflictStatus status;

    private String description;

    // códigos tipo "UKR", "RUS"
    private Set<String> countryCodes;

    public ConflictCreateUpdateDTO() {
    }

    // getters y setters

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
