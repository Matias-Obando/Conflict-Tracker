package com.example.Conflict.Tracker.dto;

import java.util.Set;

public class FactionResponseDTO {

    private Long id;
    private String name;
    private Long conflictId;
    private Set<String> supporterCountryCodes;

    public FactionResponseDTO() {
    }

    public FactionResponseDTO(Long id, String name, Long conflictId, Set<String> supporterCountryCodes) {
        this.id = id;
        this.name = name;
        this.conflictId = conflictId;
        this.supporterCountryCodes = supporterCountryCodes;
    }

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
