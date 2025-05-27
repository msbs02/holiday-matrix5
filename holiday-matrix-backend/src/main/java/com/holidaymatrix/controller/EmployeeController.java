//commenté le 27/05/2025 a 12:04 am
/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

   /* @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*

    @GetMapping("/user/{userId}")
    public ResponseEntity<EmployeeDTO> getEmployeeByUser(@PathVariable Long userId) {
        return employeeService.getEmployeeByUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesByManager(managerId));
    }

    /*@PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO dto) {
        return employeeService.createEmployee(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }*/

    /*@PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO dto) {
        return employeeService.updateEmployee(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint pour récupérer un employé par son ID utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEmployeeByUserId(@PathVariable Long userId, Principal principal) {
        try {
            // Vérifier que l'utilisateur peut accéder à ces données
            // Un manager ne peut voir que ses employés + lui-même
            // Un employé ne peut voir que ses propres données

            Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(userId);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer tous les employés d'un manager
    @GetMapping("/manager/{managerUserId}")
    public ResponseEntity<?> getEmployeesByManager(@PathVariable Long managerUserId, Principal principal) {
        try {
            List<EmployeeDTO> employees = employeeService.getEmployeesByManagerUserId(managerUserId);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des employés du manager: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer tous les employés (admin/HOS seulement)
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les employés: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer un employé par son ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Optional<EmployeeDTO> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour créer un employé
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> createdEmployee = employeeService.createEmployee(employeeDTO);
            if (createdEmployee.isPresent()) {
                return ResponseEntity.ok(createdEmployee.get());
            } else {
                return ResponseEntity.badRequest().body("Impossible de créer l'employé");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour mettre à jour un employé
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            if (updatedEmployee.isPresent()) {
                return ResponseEntity.ok(updatedEmployee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour supprimer un employé
    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }*

}*/

//commenté le 27/05/2025 a 12:20 am

/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.service.EmployeeService;
import com.holidaymatrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    // Endpoint principal pour récupérer un employé par son ID utilisateur
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getEmployeeByUserId(@PathVariable Long userId, Principal principal) {
        try {
            // Validation de sécurité : un utilisateur ne peut voir que ses propres données
            // ou les données de ses employés s'il est manager
            if (!canAccessUserData(userId, principal)) {
                return ResponseEntity.status(403).body("Accès refusé");
            }

            Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(userId);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                // Si pas trouvé dans Employee, créer un DTO basique depuis User
                return userService.findById(userId)
                        .map(user -> {
                            EmployeeDTO dto = new EmployeeDTO();
                            dto.setUserId(user.getId());
                            dto.setUsername(user.getUsername());
                            dto.setName(user.getFirstName() + " " + user.getLastName());
                            return ResponseEntity.ok(dto);
                        })
                        .orElse(ResponseEntity.notFound().build());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer les employés sous un manager
    @GetMapping("/manager/{managerUserId}/team")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getTeamByManager(@PathVariable Long managerUserId, Principal principal) {
        try {
            // Validation : un manager ne peut voir que son équipe
            if (!canAccessManagerData(managerUserId, principal)) {
                return ResponseEntity.status(403).body("Accès refusé");
            }

            List<EmployeeDTO> team = employeeService.getEmployeesByManagerUserId(managerUserId);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'équipe: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour tous les employés (admin seulement)
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les employés: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer par ID d'employé
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Optional<EmployeeDTO> employee = employeeService.getEmployeeById(id);
            return employee.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // CRUD Operations
    @PostMapping
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> created = employeeService.createEmployee(employeeDTO);
            return created.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().body("Impossible de créer l'employé"));
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> updated = employeeService.updateEmployee(id, employeeDTO);
            return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Méthodes de validation de sécurité
    private boolean canAccessUserData(Long userId, Principal principal) {
        try {
            var currentUser = userService.findByUsername(principal.getName()).orElse(null);
            if (currentUser == null) return false;

            // Admin et DG peuvent tout voir
            if (currentUser.getRole().equals("HEAD_OF_SERVICE") ||
                    currentUser.getRole().equals("DIRECTION_GENERAL")) {
                return true;
            }

            // Un utilisateur peut voir ses propres données
            if (currentUser.getId().equals(userId)) {
                return true;
            }

            // Un manager peut voir ses employés
            if (currentUser.getRole().equals("MANAGER")) {
                return employeeService.isManagerOf(currentUser.getId(), userId);
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean canAccessManagerData(Long managerUserId, Principal principal) {
        try {
            var currentUser = userService.findByUsername(principal.getName()).orElse(null);
            if (currentUser == null) return false;

            // Admin et DG peuvent tout voir
            if (currentUser.getRole().equals("HEAD_OF_SERVICE") ||
                    currentUser.getRole().equals("DIRECTION_GENERAL")) {
                return true;
            }

            // Un manager peut voir sa propre équipe
            return currentUser.getId().equals(managerUserId);
        } catch (Exception e) {
            return false;
        }
    }
}*/
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.model.User;
import com.holidaymatrix.service.EmployeeService;
import com.holidaymatrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    // Endpoint principal pour récupérer un employé par son ID utilisateur
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getEmployeeByUserId(@PathVariable Long userId, Principal principal) {
        try {
            // Validation de sécurité
            if (!canAccessUserData(userId, principal)) {
                return ResponseEntity.status(403).body("Accès refusé");
            }

            Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(userId);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                // Si pas trouvé dans Employee, créer un DTO basique depuis User
                Optional<User> userOpt = userService.findById(userId);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setUserId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setName(user.getFirstName() + " " + user.getLastName());
                    return ResponseEntity.ok(dto);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer les employés sous un manager
    @GetMapping("/manager/{managerUserId}/team")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getTeamByManager(@PathVariable Long managerUserId, Principal principal) {
        try {
            // Validation : un manager ne peut voir que son équipe
            if (!canAccessManagerData(managerUserId, principal)) {
                return ResponseEntity.status(403).body("Accès refusé");
            }

            List<EmployeeDTO> team = employeeService.getEmployeesByManagerUserId(managerUserId);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'équipe: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour tous les employés (admin seulement)
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les employés: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer par ID d'employé
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Optional<EmployeeDTO> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // CRUD Operations
    @PostMapping
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> created = employeeService.createEmployee(employeeDTO);
            if (created.isPresent()) {
                return ResponseEntity.ok(created.get());
            } else {
                return ResponseEntity.badRequest().body("Impossible de créer l'employé");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> updated = employeeService.updateEmployee(id, employeeDTO);
            if (updated.isPresent()) {
                return ResponseEntity.ok(updated.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'employé: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    // Méthodes de validation de sécurité simplifiées
    private boolean canAccessUserData(Long userId, Principal principal) {
        try {
            String username = principal.getName();
            Optional<User> currentUserOpt = userService.findByUsername(username);

            if (!currentUserOpt.isPresent()) {
                return false;
            }

            User currentUser = currentUserOpt.get();

            // Admin et DG peuvent tout voir
            String role = currentUser.getRole();
            if ("HEAD_OF_SERVICE".equals(role) || "DIRECTION_GENERAL".equals(role)) {
                return true;
            }

            // Un utilisateur peut voir ses propres données
            Long currentUserId = currentUser.getId();
            if (currentUserId.equals(userId)) {
                return true;
            }

            // Un manager peut voir ses employés
            if ("MANAGER".equals(role)) {
                return employeeService.isManagerOf(currentUserId, userId);
            }

            return false;
        } catch (Exception e) {
            System.err.println("Erreur dans canAccessUserData: " + e.getMessage());
            return false;
        }
    }

    private boolean canAccessManagerData(Long managerUserId, Principal principal) {
        try {
            String username = principal.getName();
            Optional<User> currentUserOpt = userService.findByUsername(username);

            if (!currentUserOpt.isPresent()) {
                return false;
            }

            User currentUser = currentUserOpt.get();

            // Admin et DG peuvent tout voir
            String role = currentUser.getRole();
            if ("HEAD_OF_SERVICE".equals(role) || "DIRECTION_GENERAL".equals(role)) {
                return true;
            }

            // Un manager peut voir sa propre équipe
            Long currentUserId = currentUser.getId();
            return currentUserId.equals(managerUserId);
        } catch (Exception e) {
            System.err.println("Erreur dans canAccessManagerData: " + e.getMessage());
            return false;
        }
    }
}