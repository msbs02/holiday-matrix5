/*package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matrix_entries")
public class MatrixEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matrix_id")
    private AnnualMatrix matrix;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(name = "headcount", nullable = false)
    private Integer headcount;

    @Column(name = "planned_holiday_critical")
    private Integer plannedHolidayCritical;

    @Column(name = "planned_holiday_medium")
    private Integer plannedHolidayMedium;

    @Column(name = "planned_holiday_low")
    private Integer plannedHolidayLow;

    @Column(name = "non_planned_holiday_critical")
    private Integer nonPlannedHolidayCritical;

    @Column(name = "non_planned_holiday_medium")
    private Integer nonPlannedHolidayMedium;

    @Column(name = "non_planned_holiday_low")
    private Integer nonPlannedHolidayLow;

    // Constructeurs
    public MatrixEntry() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnnualMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(AnnualMatrix matrix) {
        this.matrix = matrix;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matrix_entries")
public class MatrixEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matrix_id")
    private AnnualMatrix matrix;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    private Integer headcount;

    private Boolean plannedHolidayCritical;
    private Boolean plannedHolidayMedium;
    private Boolean plannedHolidayLow;

    private Boolean nonPlannedHolidayCritical;
    private Boolean nonPlannedHolidayMedium;
    private Boolean nonPlannedHolidayLow;

    // Constructeur
    public MatrixEntry() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnnualMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(AnnualMatrix matrix) {
        this.matrix = matrix;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
