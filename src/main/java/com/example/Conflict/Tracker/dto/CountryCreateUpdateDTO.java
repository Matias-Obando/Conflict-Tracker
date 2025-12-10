package com.example.Conflict.Tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CountryCreateUpdateDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2, max = 3)
    private String code;

    public CountryCreateUpdateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
