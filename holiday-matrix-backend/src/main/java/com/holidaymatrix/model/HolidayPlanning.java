package com.holidaymatrix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "holiday_planning")
public class HolidayPlanning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "holiday_period_id")
    private HolidayPeriod holidayPeriod;

    // RED, YELLOW, GREEN selon le cahier des charges
    private String status;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "manager_validated")
    private boolean managerValidated;

    @Column(name = "manager_validated_at")
    private LocalDateTime managerValidatedAt;

    @ManyToOne
    @JoinColumn(name = "manager_validated_by")
    private User managerValidatedBy;

    @Column(name = "hos_validated")
    private boolean hosValidated;

    @Column(name = "hos_validated_at")
    private LocalDateTime hosValidatedAt;

    @ManyToOne
    @JoinColumn(name = "hos_validated_by")
    private User hosValidatedBy;

    @Column(name = "dg_validated")
    private boolean dgValidated;

    @Column(name = "dg_validated_at")
    private LocalDateTime dgValidatedAt;

    @ManyToOne
    @JoinColumn(name = "dg_validated_by")
    private User dgValidatedBy;

    // Constructeurs
    public HolidayPlanning() {}



    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public HolidayPeriod getHolidayPeriod() {
        return holidayPeriod;
    }

    public void setHolidayPeriod(HolidayPeriod holidayPeriod) {
        this.holidayPeriod = holidayPeriod;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isManagerValidated() {
        return managerValidated;
    }

    public void setManagerValidated(boolean managerValidated) {
        this.managerValidated = managerValidated;
    }

    public LocalDateTime getManagerValidatedAt() {
        return managerValidatedAt;
    }

    public void setManagerValidatedAt(LocalDateTime managerValidatedAt) {
        this.managerValidatedAt = managerValidatedAt;
    }

    public User getManagerValidatedBy() {
        return managerValidatedBy;
    }

    public void setManagerValidatedBy(User managerValidatedBy) {
        this.managerValidatedBy = managerValidatedBy;
    }

    public boolean isHosValidated() {
        return hosValidated;
    }

    public void setHosValidated(boolean hosValidated) {
        this.hosValidated = hosValidated;
    }

    public LocalDateTime getHosValidatedAt() {
        return hosValidatedAt;
    }

    public void setHosValidatedAt(LocalDateTime hosValidatedAt) {
        this.hosValidatedAt = hosValidatedAt;
    }

    public User getHosValidatedBy() {
        return hosValidatedBy;
    }

    public void setHosValidatedBy(User hosValidatedBy) {
        this.hosValidatedBy = hosValidatedBy;
    }

    public boolean isDgValidated() {
        return dgValidated;
    }

    public void setDgValidated(boolean dgValidated) {
        this.dgValidated = dgValidated;
    }

    public LocalDateTime getDgValidatedAt() {
        return dgValidatedAt;
    }

    public void setDgValidatedAt(LocalDateTime dgValidatedAt) {
        this.dgValidatedAt = dgValidatedAt;
    }

    public User getDgValidatedBy() {
        return dgValidatedBy;
    }

    public void setDgValidatedBy(User dgValidatedBy) {
        this.dgValidatedBy = dgValidatedBy;
    }
}