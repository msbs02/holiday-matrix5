/*package com.holidaymatrix.controller;

import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import com.holidaymatrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {
        return userService.register(req);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            // Vérifier si l'utilisateur existe
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Invalid username or password"
                ));
            }

            // Vérification simplifiée du mot de passe
            if (userService.validatePassword(password, user.getPassword())) {
                // Authentification réussie

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("username", user.getUsername());
                response.put("role", user.getRole());
                response.put("id", user.getId());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Invalid username or password"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Authentication failed"
            ));
        }
    }
}*/

//src/main/java/com/holidaymatrix/controller/AuthController.java
//commenté le 26/05/2025 a 6:24
/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.JwtResponse;
import com.holidaymatrix.dto.LoginRequest;
import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.User;
import com.holidaymatrix.security.JwtUtil;
import com.holidaymatrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {

        User user = userService.register(req);
        /*if (user.getRole().equals("EMPLOYEE")) {
            Employee employee = new Employee();
            employee.setEmployeeId(user.getUsername() + "_emp");
            employee.setName(user.getFirstName() + " " + user.getLastName());
            employee.setUserId(user.getId());
            employee.setPositionId(defaultPositionId); // Set a default or provided value
            EmployeeRepository.save(employee);
        }*
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();

            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), user.getRole()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}*/

//commenté le 26/05/2025 a 6:41
/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.JwtResponse;
import com.holidaymatrix.dto.LoginRequest;
import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.User;
import com.holidaymatrix.security.JwtUtil;
import com.holidaymatrix.service.UserService;
import com.holidaymatrix.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Ajout de EmployeeService pour récupérer les infos d'équipe
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        User user = userService.register(req);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();

            // Récupérer les informations du manager si l'utilisateur est un employé
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<Employee> employee = employeeService.findByUserId(user.getId());
                    if (employee.isPresent() && employee.get().getManagerId() != null) {
                        managerId = employee.get().getManagerId();
                        Optional<User> manager = userService.findById(managerId);
                        if (manager.isPresent()) {
                            managerName = manager.get().getFirstName() + " " + manager.get().getLastName();
                        }
                    }
                }
            } catch (Exception e) {
                // Log l'erreur mais ne pas faire échouer la connexion
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec toutes les informations utilisateur
            JwtResponse response = new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName
            );

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur lors de la connexion");
        }
    }

    // Nouvel endpoint pour récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Utilisateur non authentifié");
            }

            User user = userService.findByUsername(principal.getName()).orElseThrow();

            // Récupérer les informations du manager si nécessaire
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<Employee> employee = employeeService.findByUserId(user.getId());
                    if (employee.isPresent() && employee.get().getManagerId() != null) {
                        managerId = employee.get().getManagerId();
                        Optional<User> manager = userService.findById(managerId);
                        if (manager.isPresent()) {
                            managerName = manager.get().getFirstName() + " " + manager.get().getLastName();
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec les informations utilisateur complètes
            UserDetailsResponse response = new UserDetailsResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName,
                    0 // notificationCount - à implémenter selon vos besoins
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    // Endpoint pour vérifier si l'utilisateur est connecté
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok().body("{\"authenticated\": true, \"username\": \"" + principal.getName() + "\"}");
        }
        return ResponseEntity.status(401).body("{\"authenticated\": false}");
    }
}*/

//commenté le 27/05/2025 a 12:07 am
/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.JwtResponse;
import com.holidaymatrix.dto.LoginRequest;
import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.model.User;
import com.holidaymatrix.security.JwtUtil;
import com.holidaymatrix.service.UserService;
import com.holidaymatrix.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Ajout de EmployeeService pour récupérer les infos d'équipe
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        User user = userService.register(req);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();

            // Récupérer les informations du manager si l'utilisateur est un employé
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(user.getId());
                    if (employee.isPresent()) {
                        // Prendre le manager direct comme manager principal
                        if (employee.get().getDirectManagerId() != null) {
                            managerId = employee.get().getDirectManagerId();
                            managerName = employee.get().getDirectManagerName();
                        }
                        // Si pas de manager direct mais un manager de niveau supérieur
                        else if (employee.get().getNextLevelManagerId() != null) {
                            managerId = employee.get().getNextLevelManagerId();
                            managerName = employee.get().getNextLevelManagerName();
                        }
                    }
                }
            } catch (Exception e) {
                // Log l'erreur mais ne pas faire échouer la connexion
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec toutes les informations utilisateur
            JwtResponse response = new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName
            );

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur lors de la connexion");
        }
    }

    // Nouvel endpoint pour récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Utilisateur non authentifié");
            }

            User user = userService.findByUsername(principal.getName()).orElseThrow();

            // Récupérer les informations du manager si nécessaire
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(user.getId());
                    if (employee.isPresent()) {
                        // Prendre le manager direct comme manager principal
                        if (employee.get().getDirectManagerId() != null) {
                            managerId = employee.get().getDirectManagerId();
                            managerName = employee.get().getDirectManagerName();
                        }
                        // Si pas de manager direct mais un manager de niveau supérieur
                        else if (employee.get().getNextLevelManagerId() != null) {
                            managerId = employee.get().getNextLevelManagerId();
                            managerName = employee.get().getNextLevelManagerName();
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec les informations utilisateur complètes
            UserDetailsResponse response = new UserDetailsResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName,
                    0 // notificationCount - à implémenter selon vos besoins
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    // Endpoint pour vérifier si l'utilisateur est connecté
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok().body("{\"authenticated\": true, \"username\": \"" + principal.getName() + "\"}");
        }
        return ResponseEntity.status(401).body("{\"authenticated\": false}");
    }
}*/

package com.holidaymatrix.controller;

import com.holidaymatrix.dto.JwtResponse;
import com.holidaymatrix.dto.LoginRequest;
import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.model.User;
import com.holidaymatrix.security.JwtUtil;
import com.holidaymatrix.service.UserService;
import com.holidaymatrix.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        try {
            User user = userService.register(req);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'inscription: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();

            // Récupérer les informations du manager si l'utilisateur est un employé
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(user.getId());
                    if (employee.isPresent()) {
                        // Prendre le manager direct comme manager principal
                        if (employee.get().getDirectManagerId() != null) {
                            managerId = employee.get().getDirectManagerId();
                            managerName = employee.get().getDirectManagerName();
                        }
                        // Si pas de manager direct mais un manager de niveau supérieur
                        else if (employee.get().getNextLevelManagerId() != null) {
                            managerId = employee.get().getNextLevelManagerId();
                            managerName = employee.get().getNextLevelManagerName();
                        }
                    }
                }
            } catch (Exception e) {
                // Log l'erreur mais ne pas faire échouer la connexion
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec toutes les informations utilisateur
            JwtResponse response = new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName
            );

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur lors de la connexion");
        }
    }

    // Endpoint pour récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Utilisateur non authentifié");
            }

            User user = userService.findByUsername(principal.getName()).orElseThrow();

            // Récupérer les informations du manager si nécessaire
            String managerName = null;
            Long managerId = null;

            try {
                if (user.getRole().equals("EMPLOYEE") || user.getRole().equals("MANAGER")) {
                    Optional<EmployeeDTO> employee = employeeService.getEmployeeByUser(user.getId());
                    if (employee.isPresent()) {
                        // Prendre le manager direct comme manager principal
                        if (employee.get().getDirectManagerId() != null) {
                            managerId = employee.get().getDirectManagerId();
                            managerName = employee.get().getDirectManagerName();
                        }
                        // Si pas de manager direct mais un manager de niveau supérieur
                        else if (employee.get().getNextLevelManagerId() != null) {
                            managerId = employee.get().getNextLevelManagerId();
                            managerName = employee.get().getNextLevelManagerName();
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du manager: " + e.getMessage());
            }

            // Créer la réponse avec les informations utilisateur complètes
            UserDetailsResponse response = new UserDetailsResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDepartment(),
                    user.getPosition(),
                    managerId,
                    managerName,
                    0 // notificationCount - à implémenter selon vos besoins
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    // Endpoint pour vérifier si l'utilisateur est connecté
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok().body("{\"authenticated\": true, \"username\": \"" + principal.getName() + "\"}");
        }
        return ResponseEntity.status(401).body("{\"authenticated\": false}");
    }

    // Endpoint pour obtenir des informations contextuelles sur l'utilisateur
    @GetMapping("/context")
    public ResponseEntity<?> getUserContext(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Utilisateur non authentifié");
            }

            User user = userService.findByUsername(principal.getName()).orElseThrow();

            // Informations contextuelles selon le rôle
            String context = switch (user.getRole()) {
                case "MANAGER" -> {
                    long teamSize = employeeService.countEmployeesByManager(user.getId());
                    yield "Manager avec " + teamSize + " employé(s) sous sa responsabilité";
                }
                case "HEAD_OF_SERVICE" -> "Responsable de service avec accès global";
                case "DIRECTION_GENERAL" -> "Direction générale avec tous les privilèges";
                case "EMPLOYEE" -> "Employé avec accès aux fonctionnalités de base";
                default -> "Utilisateur standard";
            };

            return ResponseEntity.ok().body("{\"context\": \"" + context + "\", \"role\": \"" + user.getRole() + "\"}");
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du contexte: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }
}