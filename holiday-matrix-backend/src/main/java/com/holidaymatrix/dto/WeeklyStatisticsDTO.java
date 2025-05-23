/*package com.holidaymatrix.dto;

public class WeeklyStatisticsDTO {
    private int weekNumber;
    private int plannedHolidays;

    // Constructeur par défaut
    public WeeklyStatisticsDTO() {
    }

    // Constructeur avec paramètres
    public WeeklyStatisticsDTO(int weekNumber, int plannedHolidays) {
        this.weekNumber = weekNumber;
        this.plannedHolidays = plannedHolidays;
    }

    // Getters et setters
    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getPlannedHolidays() {
        return plannedHolidays;
    }

    public void setPlannedHolidays(int plannedHolidays) {
        this.plannedHolidays = plannedHolidays;
    }

    @Override
    public String toString() {
        return "WeeklyStatisticsDTO{" +
                "weekNumber=" + weekNumber +
                ", plannedHolidays=" + plannedHolidays +
                '}';
    }
}
*/

// WeeklyStatisticsDTO.java
package com.holidaymatrix.dto;

public class WeeklyStatisticsDTO {
    private int weekNumber;
    private int plannedHolidays;
    private String criticalityLevel;

    // Constructeurs
    public WeeklyStatisticsDTO() {}

    public WeeklyStatisticsDTO(int weekNumber, int plannedHolidays) {
        this.weekNumber = weekNumber;
        this.plannedHolidays = plannedHolidays;
    }

    public WeeklyStatisticsDTO(int weekNumber, int plannedHolidays, String criticalityLevel) {
        this.weekNumber = weekNumber;
        this.plannedHolidays = plannedHolidays;
        this.criticalityLevel = criticalityLevel;
    }

    // Getters et Setters
    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getPlannedHolidays() {
        return plannedHolidays;
    }

    public void setPlannedHolidays(int plannedHolidays) {
        this.plannedHolidays = plannedHolidays;
    }

    public String getCriticalityLevel() {
        return criticalityLevel;
    }

    public void setCriticalityLevel(String criticalityLevel) {
        this.criticalityLevel = criticalityLevel;
    }
}