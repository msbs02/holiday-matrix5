/*package com.holidaymatrix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "annual_matrices")
public class AnnualMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

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

    @OneToMany(mappedBy = "matrix", cascade = CascadeType.ALL)
    private List<MatrixEntry> entries;

    // Constructeurs
    public AnnualMatrix() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public List<MatrixEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MatrixEntry> entries) {
        this.entries = entries;
    }

    // Méthode pour ajouter une entrée
    public void addEntry(MatrixEntry entry) {
        entries.add(entry);
        entry.setMatrix(this);
    }
}
*/
//make en commentaire le 10/05/2025 a 22:26
/*
package com.holidaymatrix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "annual_matrices")
public class AnnualMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La position est obligatoire")
    private String position;

    // HC = Headcount
    private int hc;

    private String description;

    @NotBlank(message = "Le manager direct est obligatoire")
    private String directManager;

    @NotBlank(message = "L'organisation est obligatoire")
    private String organization;

    private String nextLevelManager;

    private int employeeCount;

    // Niveau de criticité pour les congés planifiés: 1 = Faible, 2 = Moyen, 3 = Élevé
    private int plannedHolidayCriticality;

    // Niveau de criticité pour les congés non planifiés: 1 = Faible, 2 = Moyen, 3 = Élevé
    private int nonPlannedHolidayCriticality;

    @NotNull(message = "L'année est obligatoire")
    private int year;

    @Enumerated(EnumType.STRING)
    private ValidationStatus managerValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus hosValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus dgValidationStatus = ValidationStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructeur par défaut
    public AnnualMatrix() {}

    // Enum pour les statuts de validation
    public enum ValidationStatus {
        PENDING, APPROVED, REJECTED
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHc() {
        return hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(String nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getPlannedHolidayCriticality() {
        return plannedHolidayCriticality;
    }

    public void setPlannedHolidayCriticality(int plannedHolidayCriticality) {
        this.plannedHolidayCriticality = plannedHolidayCriticality;
    }

    public int getNonPlannedHolidayCriticality() {
        return nonPlannedHolidayCriticality;
    }

    public void setNonPlannedHolidayCriticality(int nonPlannedHolidayCriticality) {
        this.nonPlannedHolidayCriticality = nonPlannedHolidayCriticality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ValidationStatus getManagerValidationStatus() {
        return managerValidationStatus;
    }

    public void setManagerValidationStatus(ValidationStatus managerValidationStatus) {
        this.managerValidationStatus = managerValidationStatus;
    }

    public ValidationStatus getHosValidationStatus() {
        return hosValidationStatus;
    }

    public void setHosValidationStatus(ValidationStatus hosValidationStatus) {
        this.hosValidationStatus = hosValidationStatus;
    }

    public ValidationStatus getDgValidationStatus() {
        return dgValidationStatus;
    }

    public void setDgValidationStatus(ValidationStatus dgValidationStatus) {
        this.dgValidationStatus = dgValidationStatus;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}*/
/*
package com.holidaymatrix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annual_matrices")
public class AnnualMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDateTime createdAt;

    private boolean hosValidated;

    @ManyToOne
    @JoinColumn(name = "hos_validated_by_id")
    private User hosValidatedBy;

    private LocalDateTime hosValidatedAt;

    private boolean dgValidated;

    @ManyToOne
    @JoinColumn(name = "dg_validated_by_id")
    private User dgValidatedBy;

    private LocalDateTime dgValidatedAt;

    @OneToMany(mappedBy = "matrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatrixEntry> entries = new ArrayList<>();

    // Enum pour les statuts de validation
    public enum ValidationStatus {
        PENDING, APPROVED, REJECTED
    }

    // Constructeurs
    public AnnualMatrix() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public boolean isHosValidated() {
        return hosValidated;
    }

    public void setHosValidated(boolean hosValidated) {
        this.hosValidated = hosValidated;
    }

    public User getHosValidatedBy() {
        return hosValidatedBy;
    }

    public void setHosValidatedBy(User hosValidatedBy) {
        this.hosValidatedBy = hosValidatedBy;
    }

    public LocalDateTime getHosValidatedAt() {
        return hosValidatedAt;
    }

    public void setHosValidatedAt(LocalDateTime hosValidatedAt) {
        this.hosValidatedAt = hosValidatedAt;
    }

    public boolean isDgValidated() {
        return dgValidated;
    }

    public void setDgValidated(boolean dgValidated) {
        this.dgValidated = dgValidated;
    }

    public User getDgValidatedBy() {
        return dgValidatedBy;
    }

    public void setDgValidatedBy(User dgValidatedBy) {
        this.dgValidatedBy = dgValidatedBy;
    }

    public LocalDateTime getDgValidatedAt() {
        return dgValidatedAt;
    }

    public void setDgValidatedAt(LocalDateTime dgValidatedAt) {
        this.dgValidatedAt = dgValidatedAt;
    }

    public List<MatrixEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MatrixEntry> entries) {
        this.entries = entries;
    }
}*/
/*
package com.holidaymatrix.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "annual_matrices")
public class AnnualMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    private int hc;

    private String description;

    private String directManager;

    private String organization;

    private String nextLevelManager;

    private int employeeCount;

    private int plannedHolidayCriticality;

    private int nonPlannedHolidayCriticality;

    @Column(nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    private ValidationStatus managerValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus hosValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus dgValidationStatus = ValidationStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Enum pour les statuts de validation
    public enum ValidationStatus {
        PENDING, APPROVED, REJECTED
    }

    // Constructeurs
    public AnnualMatrix() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHc() {
        return hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(String nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getPlannedHolidayCriticality() {
        return plannedHolidayCriticality;
    }

    public void setPlannedHolidayCriticality(int plannedHolidayCriticality) {
        this.plannedHolidayCriticality = plannedHolidayCriticality;
    }

    public int getNonPlannedHolidayCriticality() {
        return nonPlannedHolidayCriticality;
    }

    public void setNonPlannedHolidayCriticality(int nonPlannedHolidayCriticality) {
        this.nonPlannedHolidayCriticality = nonPlannedHolidayCriticality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ValidationStatus getManagerValidationStatus() {
        return managerValidationStatus;
    }

    public void setManagerValidationStatus(ValidationStatus managerValidationStatus) {
        this.managerValidationStatus = managerValidationStatus;
    }

    public ValidationStatus getHosValidationStatus() {
        return hosValidationStatus;
    }

    public void setHosValidationStatus(ValidationStatus hosValidationStatus) {
        this.hosValidationStatus = hosValidationStatus;
    }

    public ValidationStatus getDgValidationStatus() {
        return dgValidationStatus;
    }

    public void setDgValidationStatus(ValidationStatus dgValidationStatus) {
        this.dgValidationStatus = dgValidationStatus;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
*/

package com.holidaymatrix.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annual_matrices")
public class AnnualMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Attributs existants
    @Column(nullable = false)
    private String position;

    private int hc;

    private String description;

    private String directManager;

    private String organization;

    private String nextLevelManager;

    private int employeeCount;

    private int plannedHolidayCriticality;

    private int nonPlannedHolidayCriticality;

    @Column(nullable = false)
    private Integer year;

    // Nouveaux attributs pour la validation
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private boolean hosValidated;

    @ManyToOne
    @JoinColumn(name = "hos_validated_by_id")
    private User hosValidatedBy;

    private LocalDateTime hosValidatedAt;

    private boolean dgValidated;

    @ManyToOne
    @JoinColumn(name = "dg_validated_by_id")
    private User dgValidatedBy;

    private LocalDateTime dgValidatedAt;

    @OneToMany(mappedBy = "matrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatrixEntry> entries = new ArrayList<>();

    // Attributs existants
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    private ValidationStatus managerValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus hosValidationStatus = ValidationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ValidationStatus dgValidationStatus = ValidationStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Enum pour les statuts de validation
    public enum ValidationStatus {
        PENDING, APPROVED, REJECTED
    }

    // Constructeurs
    public AnnualMatrix() {}

    // Getters & Setters pour les attributs existants
    // ...




    // Nouveaux getters & setters pour la validation
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isHosValidated() {
        return hosValidated;
    }

    public void setHosValidated(boolean hosValidated) {
        this.hosValidated = hosValidated;
    }

    public User getHosValidatedBy() {
        return hosValidatedBy;
    }

    public void setHosValidatedBy(User hosValidatedBy) {
        this.hosValidatedBy = hosValidatedBy;
    }

    public LocalDateTime getHosValidatedAt() {
        return hosValidatedAt;
    }

    public void setHosValidatedAt(LocalDateTime hosValidatedAt) {
        this.hosValidatedAt = hosValidatedAt;
    }

    public boolean isDgValidated() {
        return dgValidated;
    }

    public void setDgValidated(boolean dgValidated) {
        this.dgValidated = dgValidated;
    }

    public User getDgValidatedBy() {
        return dgValidatedBy;
    }

    public void setDgValidatedBy(User dgValidatedBy) {
        this.dgValidatedBy = dgValidatedBy;
    }

    public LocalDateTime getDgValidatedAt() {
        return dgValidatedAt;
    }

    public void setDgValidatedAt(LocalDateTime dgValidatedAt) {
        this.dgValidatedAt = dgValidatedAt;
    }

    public List<MatrixEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MatrixEntry> entries) {
        this.entries = entries;
    }

    // Getters & Setters existants
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHc() {
        return hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getNextLevelManager() {
        return nextLevelManager;
    }

    public void setNextLevelManager(String nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getPlannedHolidayCriticality() {
        return plannedHolidayCriticality;
    }

    public void setPlannedHolidayCriticality(int plannedHolidayCriticality) {
        this.plannedHolidayCriticality = plannedHolidayCriticality;
    }

    public int getNonPlannedHolidayCriticality() {
        return nonPlannedHolidayCriticality;
    }

    public void setNonPlannedHolidayCriticality(int nonPlannedHolidayCriticality) {
        this.nonPlannedHolidayCriticality = nonPlannedHolidayCriticality;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ValidationStatus getManagerValidationStatus() {
        return managerValidationStatus;
    }

    public void setManagerValidationStatus(ValidationStatus managerValidationStatus) {
        this.managerValidationStatus = managerValidationStatus;
    }

    public ValidationStatus getHosValidationStatus() {
        return hosValidationStatus;
    }

    public void setHosValidationStatus(ValidationStatus hosValidationStatus) {
        this.hosValidationStatus = hosValidationStatus;
    }

    public ValidationStatus getDgValidationStatus() {
        return dgValidationStatus;
    }

    public void setDgValidationStatus(ValidationStatus dgValidationStatus) {
        this.dgValidationStatus = dgValidationStatus;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}