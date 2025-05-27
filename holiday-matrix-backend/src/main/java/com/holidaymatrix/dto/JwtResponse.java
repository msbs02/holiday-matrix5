//commenté le 26/05/2025 a 6:23
/*
package com.holidaymatrix.dto;

public class JwtResponse {
    private String token;
    private String username;
    private String role;

    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}*/

//commenté le 27/05/2025 a 12:11 am
/*
package com.holidaymatrix.dto;

public class JwtResponse {
    private String token;
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

    // Constructeur principal avec toutes les informations
    public JwtResponse(String token, Long id, String username, String role,
                       String email, String firstName, String lastName,
                       String department, String position, Long managerId, String managerName) {
        this.token = token;
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
    }

    // Constructeur de compatibilité (garde votre code existant fonctionnel)
    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return token;
    }

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

    // Setters (optionnels, mais utiles)
    public void setToken(String token) {
        this.token = token;
    }

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
}*/

package com.holidaymatrix.dto;

public class JwtResponse {
    private String token;
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

    // Constructeur par défaut
    public JwtResponse() {
    }

    // Constructeur principal avec toutes les informations
    public JwtResponse(String token, Long id, String username, String role,
                       String email, String firstName, String lastName,
                       String department, String position, Long managerId, String managerName) {
        this.token = token;
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
    }

    // Constructeur de compatibilité (garde votre code existant fonctionnel)
    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() { return token; }
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

    // Setters
    public void setToken(String token) { this.token = token; }
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
}