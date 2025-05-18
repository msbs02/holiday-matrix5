/*package com.holidaymatrix.dto;

public class MatrixEntryDTO {
    private Long id;
    private Long matrixId;
    private Long positionId;
    private String positionTitle;
    private Integer headcount;
    private Integer plannedHolidayCritical;
    private Integer plannedHolidayMedium;
    private Integer plannedHolidayLow;
    private Integer nonPlannedHolidayCritical;
    private Integer nonPlannedHolidayMedium;
    private Integer nonPlannedHolidayLow;

    // Constructeurs
    public MatrixEntryDTO() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(Long matrixId) {
        this.matrixId = matrixId;
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

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public Integer getPlannedHolidayCritical() {
        return plannedHolidayCritical;
    }

    public void setPlannedHolidayCritical(Integer plannedHolidayCritical) {
        this.plannedHolidayCritical = plannedHolidayCritical;
    }

    public Integer getPlannedHolidayMedium() {
        return plannedHolidayMedium;
    }

    public void setPlannedHolidayMedium(Integer plannedHolidayMedium) {
        this.plannedHolidayMedium = plannedHolidayMedium;
    }

    public Integer getPlannedHolidayLow() {
        return plannedHolidayLow;
    }

    public void setPlannedHolidayLow(Integer plannedHolidayLow) {
        this.plannedHolidayLow = plannedHolidayLow;
    }

    public Integer getNonPlannedHolidayCritical() {
        return nonPlannedHolidayCritical;
    }

    public void setNonPlannedHolidayCritical(Integer nonPlannedHolidayCritical) {
        this.nonPlannedHolidayCritical = nonPlannedHolidayCritical;
    }

    public Integer getNonPlannedHolidayMedium() {
        return nonPlannedHolidayMedium;
    }

    public void setNonPlannedHolidayMedium(Integer nonPlannedHolidayMedium) {
        this.nonPlannedHolidayMedium = nonPlannedHolidayMedium;
    }

    public Integer getNonPlannedHolidayLow() {
        return nonPlannedHolidayLow;
    }

    public void setNonPlannedHolidayLow(Integer nonPlannedHolidayLow) {
        this.nonPlannedHolidayLow = nonPlannedHolidayLow;
    }
}*/
package com.holidaymatrix.dto;

public class MatrixEntryDTO {
    private Long id;
    private Long matrixId;
    private Long positionId;
    private String positionTitle;
    private Integer headcount;
    private Boolean plannedHolidayCritical;
    private Boolean plannedHolidayMedium;
    private Boolean plannedHolidayLow;
    private Boolean nonPlannedHolidayCritical;
    private Boolean nonPlannedHolidayMedium;
    private Boolean nonPlannedHolidayLow;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(Long matrixId) {
        this.matrixId = matrixId;
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

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public Boolean getPlannedHolidayCritical() {
        return plannedHolidayCritical;
    }

    public void setPlannedHolidayCritical(Boolean plannedHolidayCritical) {
        this.plannedHolidayCritical = plannedHolidayCritical;
    }

    public Boolean getPlannedHolidayMedium() {
        return plannedHolidayMedium;
    }

    public void setPlannedHolidayMedium(Boolean plannedHolidayMedium) {
        this.plannedHolidayMedium = plannedHolidayMedium;
    }

    public Boolean getPlannedHolidayLow() {
        return plannedHolidayLow;
    }

    public void setPlannedHolidayLow(Boolean plannedHolidayLow) {
        this.plannedHolidayLow = plannedHolidayLow;
    }

    public Boolean getNonPlannedHolidayCritical() {
        return nonPlannedHolidayCritical;
    }

    public void setNonPlannedHolidayCritical(Boolean nonPlannedHolidayCritical) {
        this.nonPlannedHolidayCritical = nonPlannedHolidayCritical;
    }

    public Boolean getNonPlannedHolidayMedium() {
        return nonPlannedHolidayMedium;
    }

    public void setNonPlannedHolidayMedium(Boolean nonPlannedHolidayMedium) {
        this.nonPlannedHolidayMedium = nonPlannedHolidayMedium;
    }

    public Boolean getNonPlannedHolidayLow() {
        return nonPlannedHolidayLow;
    }

    public void setNonPlannedHolidayLow(Boolean nonPlannedHolidayLow) {
        this.nonPlannedHolidayLow = nonPlannedHolidayLow;
    }
}