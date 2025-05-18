package com.holidaymatrix.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    private String content;

    private Long matrixId;

    private Long holidayId;

    // Constructeur
    public CommentRequest() {}

    // Getters & Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(Long matrixId) {
        this.matrixId = matrixId;
    }

    public Long getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Long holidayId) {
        this.holidayId = holidayId;
    }
}