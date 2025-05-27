//make en comment le 26/05/2025 a 6 :26
/*
package com.holidaymatrix.dto;

public class UserDetailsResponse {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private Long managerId;
    private String managerName;
    private int notificationCount;

    // Constructeur
    public UserDetailsResponse() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}*/
//commenté le 26/05/2025 a 6:51
/*
package com.holidaymatrix.dto;

public class UserDetailsResponse {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private Long managerId;
    private String managerName;
    private int notificationCount;

    public UserDetailsResponse(Long id, String username, String role, String email,
                               String firstName, String lastName, String department,
                               String position, Long managerId, String managerName,
                               int notificationCount) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.managerId = managerId;
        this.managerName = managerName;
        this.notificationCount = notificationCount;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public Long getManagerId() {
        return managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}*/
//commenté le 27/05/2025 a 12:14 am
/*package com.holidaymatrix.dto;

public class UserDetailsResponse {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private Long managerId;
    private String managerName;
    private int notificationCount;

    // Constructeur par défaut (nécessaire pour les setters)
    public UserDetailsResponse() {
    }

    // Constructeur principal avec toutes les informations
    public UserDetailsResponse(Long id, String username, String role, String email,
                               String firstName, String lastName, String department,
                               String position, Long managerId, String managerName,
                               int notificationCount) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.managerId = managerId;
        this.managerName = managerName;
        this.notificationCount = notificationCount;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public Long getManagerId() {
        return managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}*/
package com.holidaymatrix.dto;

public class UserDetailsResponse {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private Long managerId;
    private String managerName;
    private int notificationCount;

    // Constructeur par défaut (nécessaire pour les setters)
    public UserDetailsResponse() {
    }

    // Constructeur principal avec toutes les informations
    public UserDetailsResponse(Long id, String username, String role, String email,
                               String firstName, String lastName, String department,
                               String position, Long managerId, String managerName,
                               int notificationCount) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.managerId = managerId;
        this.managerName = managerName;
        this.notificationCount = notificationCount;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public Long getManagerId() { return managerId; }
    public String getManagerName() { return managerName; }
    public int getNotificationCount() { return notificationCount; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public void setNotificationCount(int notificationCount) { this.notificationCount = notificationCount; }
}