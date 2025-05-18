package com.holidaymatrix.dto;

import jakarta.validation.constraints.NotNull;

public class ValidationRequest {
    @NotNull(message = "L'identifiant de l'élément est obligatoire")
    private Long id;

    @NotNull(message = "Le statut de validation est obligatoire")
    private String status; // APPROVED, REJECTED

    private String comment;

    // Constructeur
    public ValidationRequest() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}