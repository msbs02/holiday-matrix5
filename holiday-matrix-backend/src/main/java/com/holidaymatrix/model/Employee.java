package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "direct_manager_id")
    private Employee directManager;

    @ManyToOne
    @JoinColumn(name = "next_level_manager_id")
    private Employee nextLevelManager;

    // Constructeurs
    public Employee() {}

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee getDirectManager() {
        return directManager;
    }

    public void setDirectManager(Employee directManager) {
        this.directManager = directManager;
    }

    public Employee getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(Employee nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }
}