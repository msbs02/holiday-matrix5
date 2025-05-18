/*package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private boolean isCritical;

    // Constructeurs
    public Position() {}

    public Position(String title, String description, Organization organization, boolean isCritical) {
        this.title = title;
        this.description = description;
        this.organization = organization;
        this.isCritical = isCritical;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }
}*/

/*package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String organization;

    @ManyToOne
    @JoinColumn(name = "direct_manager_id")
    private User directManager;

    @ManyToOne
    @JoinColumn(name = "next_level_manager_id")
    private User nextLevelManager;

    // Constructeur
    public Position() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public User getDirectManager() {
        return directManager;
    }

    public void setDirectManager(User directManager) {
        this.directManager = directManager;
    }

    public User getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(User nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }
}*/
package com.holidaymatrix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private boolean isCritical;

    @ManyToOne
    @JoinColumn(name = "direct_manager_id")
    private User directManager;

    @ManyToOne
    @JoinColumn(name = "next_level_manager_id")
    private User nextLevelManager;

    // Constructeur
    public Position() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        this.isCritical = critical;
    }

    public User getDirectManager() {
        return directManager;
    }

    public void setDirectManager(User directManager) {
        this.directManager = directManager;
    }

    public User getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(User nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }
}