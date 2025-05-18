package com.holidaymatrix.dto;

public class OrganizationDTO {
    private Long id;
    private String name;

    // Constructeurs
    public OrganizationDTO() {}

    public OrganizationDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters et Setters
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
}