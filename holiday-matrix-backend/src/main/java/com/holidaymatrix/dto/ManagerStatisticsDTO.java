/*package com.holidaymatrix.dto;

import java.util.List;

public class ManagerStatisticsDTO {
    private String managerName;
    private String managerId;
    private List<WeeklyStatisticsDTO> weeklyStatistics;

    // Constructeur par défaut
    public ManagerStatisticsDTO() {}

    // Constructeur avec paramètres
    public ManagerStatisticsDTO(String managerName, String managerId, List<WeeklyStatisticsDTO> weeklyStatistics) {
        this.managerName = managerName;
        this.managerId = managerId;
        this.weeklyStatistics = weeklyStatistics;
    }

    // Getters et Setters

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<WeeklyStatisticsDTO> getWeeklyStatistics() {
        return weeklyStatistics;
    }

    public void setWeeklyStatistics(List<WeeklyStatisticsDTO> weeklyStatistics) {
        this.weeklyStatistics = weeklyStatistics;
    }
}
*/



// ManagerStatisticsDTO.java
package com.holidaymatrix.dto;

import java.util.List;

public class ManagerStatisticsDTO {
    private String managerId;
    private String managerName;
    private List<WeeklyStatisticsDTO> weeklyStatistics;
    private Integer totalEmployees;

    // Constructeurs
    public ManagerStatisticsDTO() {}

    public ManagerStatisticsDTO(String managerId, String managerName, List<WeeklyStatisticsDTO> weeklyStatistics) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.weeklyStatistics = weeklyStatistics;
    }

    // Getters et Setters
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<WeeklyStatisticsDTO> getWeeklyStatistics() {
        return weeklyStatistics;
    }

    public void setWeeklyStatistics(List<WeeklyStatisticsDTO> weeklyStatistics) {
        this.weeklyStatistics = weeklyStatistics;
    }

    public Integer getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(Integer totalEmployees) {
        this.totalEmployees = totalEmployees;
    }
}



