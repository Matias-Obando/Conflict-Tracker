package com.example.Conflict.Tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class FactionCreateUpdateDTO {

    @NotBlank
    private String name;

    @NotNull
    private Long conflictId;

    // códigos de país que apoyan la facción
    @NotNull
    private Set<@Size(min = 2, max = 3) String> supporterCountryCodes;

    public FactionCreateUpdateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getConflictId() {
        return conflictId;
    }

    public void setConflictId(Long conflictId) {
        this.conflictId = conflictId;
    }

    public Set<String> getSupporterCountryCodes() {
        return supporterCountryCodes;
    }

    public void setSupporterCountryCodes(Set<String> supporterCountryCodes) {
        this.supporterCountryCodes = supporterCountryCodes;
    }
}
