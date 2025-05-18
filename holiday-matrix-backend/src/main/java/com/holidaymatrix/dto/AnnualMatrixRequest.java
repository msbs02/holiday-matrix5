package com.holidaymatrix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnnualMatrixRequest {
    @NotBlank(message = "La position est obligatoire")
    private String position;

    private int hc;

    private String description;

    @NotBlank(message = "Le manager direct est obligatoire")
    private String directManager;

    @NotBlank(message = "L'organisation est obligatoire")
    private String organization;

    private String nextLevelManager;

    private int employeeCount;

    private int plannedHolidayCriticality;

    private int nonPlannedHolidayCriticality;

    @NotNull(message = "L'année est obligatoire")
    private int year;

    // Constructeur
    public AnnualMatrixRequest() {}

    // Getters & Setters
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHc() {
        return hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(String nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getPlannedHolidayCriticality() {
        return plannedHolidayCriticality;
    }

    public void setPlannedHolidayCriticality(int plannedHolidayCriticality) {
        this.plannedHolidayCriticality = plannedHolidayCriticality;
    }

    public int getNonPlannedHolidayCriticality() {
        return nonPlannedHolidayCriticality;
    }

    public void setNonPlannedHolidayCriticality(int nonPlannedHolidayCriticality) {
        this.nonPlannedHolidayCriticality = nonPlannedHolidayCriticality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}