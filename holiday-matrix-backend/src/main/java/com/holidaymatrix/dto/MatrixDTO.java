package com.holidaymatrix.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MatrixDTO {
    private Long id;
    private Integer year;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private boolean hosValidated;
    private LocalDateTime hosValidatedAt;
    private Long hosValidatedById;
    private String hosValidatedByUsername;
    private boolean dgValidated;
    private LocalDateTime dgValidatedAt;
    private Long dgValidatedById;
    private String dgValidatedByUsername;
    private List<MatrixEntryDTO> entries;

    // Constructeurs
    public MatrixDTO() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isHosValidated() {
        return hosValidated;
    }

    public void setHosValidated(boolean hosValidated) {
        this.hosValidated = hosValidated;
    }

    public LocalDateTime getHosValidatedAt() {
        return hosValidatedAt;
    }

    public void setHosValidatedAt(LocalDateTime hosValidatedAt) {
        this.hosValidatedAt = hosValidatedAt;
    }

    public Long getHosValidatedById() {
        return hosValidatedById;
    }

    public void setHosValidatedById(Long hosValidatedById) {
        this.hosValidatedById = hosValidatedById;
    }

    public String getHosValidatedByUsername() {
        return hosValidatedByUsername;
    }

    public void setHosValidatedByUsername(String hosValidatedByUsername) {
        this.hosValidatedByUsername = hosValidatedByUsername;
    }

    public boolean isDgValidated() {
        return dgValidated;
    }

    public void setDgValidated(boolean dgValidated) {
        this.dgValidated = dgValidated;
    }

    public LocalDateTime getDgValidatedAt() {
        return dgValidatedAt;
    }

    public void setDgValidatedAt(LocalDateTime dgValidatedAt) {
        this.dgValidatedAt = dgValidatedAt;
    }

    public Long getDgValidatedById() {
        return dgValidatedById;
    }

    public void setDgValidatedById(Long dgValidatedById) {
        this.dgValidatedById = dgValidatedById;
    }

    public String getDgValidatedByUsername() {
        return dgValidatedByUsername;
    }

    public void setDgValidatedByUsername(String dgValidatedByUsername) {
        this.dgValidatedByUsername = dgValidatedByUsername;
    }

    public List<MatrixEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<MatrixEntryDTO> entries) {
        this.entries = entries;
    }
}