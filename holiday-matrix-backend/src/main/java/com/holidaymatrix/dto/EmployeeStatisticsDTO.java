// EmployeeStatisticsDTO.java (pour des statistiques plus détaillées)
package com.holidaymatrix.dto;

public class EmployeeStatisticsDTO {
    private Long employeeId;
    private String employeeName;
    private String position;
    private int totalHolidayDays;
    private int plannedHolidays;
    private int unplannedHolidays;

    // Constructeurs
    public EmployeeStatisticsDTO() {}

    public EmployeeStatisticsDTO(Long employeeId, String employeeName, String position) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.position = position;
    }

    // Getters et Setters
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTotalHolidayDays() {
        return totalHolidayDays;
    }

    public void setTotalHolidayDays(int totalHolidayDays) {
        this.totalHolidayDays = totalHolidayDays;
    }

    public int getPlannedHolidays() {
        return plannedHolidays;
    }

    public void setPlannedHolidays(int plannedHolidays) {
        this.plannedHolidays = plannedHolidays;
    }

    public int getUnplannedHolidays() {
        return unplannedHolidays;
    }

    public void setUnplannedHolidays(int unplannedHolidays) {
        this.unplannedHolidays = unplannedHolidays;
    }
}