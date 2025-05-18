package com.holidaymatrix.dto;

public class EmployeeDTO {
    private Long id;
    private String employeeId;
    private String name;
    private Long positionId;
    private String positionTitle;
    private Long userId;
    private String username;
    private Long directManagerId;
    private String directManagerName;
    private Long nextLevelManagerId;
    private String nextLevelManagerName;

    // Constructeurs
    public EmployeeDTO() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDirectManagerId() {
        return directManagerId;
    }

    public void setDirectManagerId(Long directManagerId) {
        this.directManagerId = directManagerId;
    }

    public String getDirectManagerName() {
        return directManagerName;
    }

    public void setDirectManagerName(String directManagerName) {
        this.directManagerName = directManagerName;
    }

    public Long getNextLevelManagerId() {
        return nextLevelManagerId;
    }

    public void setNextLevelManagerId(Long nextLevelManagerId) {
        this.nextLevelManagerId = nextLevelManagerId;
    }

    public String getNextLevelManagerName() {
        return nextLevelManagerName;
    }

    public void setNextLevelManagerName(String nextLevelManagerName) {
        this.nextLevelManagerName = nextLevelManagerName;
    }
}