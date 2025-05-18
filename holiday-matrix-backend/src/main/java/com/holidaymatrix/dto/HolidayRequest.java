package com.holidaymatrix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class HolidayRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate endDate;

    private boolean isPlanned;

    private int criticalityLevel;

    // Constructeur
    public HolidayRequest() {}

    // Getters & Setters
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isPlanned() {
        return isPlanned;
    }

    public void setPlanned(boolean planned) {
        isPlanned = planned;
    }

    public int getCriticalityLevel() {
        return criticalityLevel;
    }

    public void setCriticalityLevel(int criticalityLevel) {
        this.criticalityLevel = criticalityLevel;
    }
}