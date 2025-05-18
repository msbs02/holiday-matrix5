package com.holidaymatrix.dto;

import java.time.LocalDateTime;

public class HolidayPlanningDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long holidayPeriodId;
    private String holidayPeriodName;
    private String status;
    private String comment;
    private boolean managerValidated;
    private boolean hosValidated;
    private boolean dgValidated;

    // Constructeurs
    public HolidayPlanningDTO() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getHolidayPeriodId() {
        return holidayPeriodId;
    }

    public void setHolidayPeriodId(Long holidayPeriodId) {
        this.holidayPeriodId = holidayPeriodId;
    }

    public String getHolidayPeriodName() {
        return holidayPeriodName;
    }

    public void setHolidayPeriodName(String holidayPeriodName) {
        this.holidayPeriodName = holidayPeriodName;
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

    public boolean isManagerValidated() {
        return managerValidated;
    }

    public void setManagerValidated(boolean managerValidated) {
        this.managerValidated = managerValidated;
    }

    public boolean isHosValidated() {
        return hosValidated;
    }

    public void setHosValidated(boolean hosValidated) {
        this.hosValidated = hosValidated;
    }

    public boolean isDgValidated() {
        return dgValidated;
    }

    public void setDgValidated(boolean dgValidated) {
        this.dgValidated = dgValidated;
    }
}