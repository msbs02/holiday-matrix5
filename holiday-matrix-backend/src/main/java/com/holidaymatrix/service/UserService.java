/*package com.holidaymatrix.service;

import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.holidaymatrix.dto.RegisterRequest;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // méthode existante
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // nouvelle méthode
    public User register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(req.getPassword()); // pour débuter, sans encodage
        u.setRole(req.getRole() != null ? req.getRole() : "USER");
        return userRepository.save(u);
    }
}*/

// make en commentaire le 10/05/2025 a 18:12
/*package com.holidaymatrix.service;

import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import com.holidaymatrix.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Méthode existante
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Méthode mise à jour pour l'inscription
    public User register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // Encodage du mot de passe
        u.setRole(req.getRole() != null ? req.getRole() : "USER");
        return userRepository.save(u);
    }
    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}*/

//UserService.java
//commenté le 26/05/2025 a 6:53
/*
package com.holidaymatrix.service;

import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    // Méthode existante
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Méthode mise à jour pour l'inscription
    public User register(RegisterRequest req) {
        logger.info("Registering new user: {}", req.getUsername());

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", req.getUsername());
            throw new RuntimeException("Username already exists");
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // Encodage du mot de passe
        u.setRole(req.getRole() != null ? req.getRole() : "USER");

        // Définir les nouveaux champs
        u.setEmail(req.getEmail());
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setDepartment(req.getDepartment());
        u.setPosition(req.getPosition());

        return userRepository.save(u);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public UserDetailsResponse getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        UserDetailsResponse response = new UserDetailsResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDepartment(user.getDepartment());
        response.setPosition(user.getPosition());

        if (user.getManager() != null) {
            response.setManagerId(user.getManager().getId());
            response.setManagerName(user.getManager().getUsername());
        }

        // Nombre de notifications non lues
        response.setNotificationCount((int) notificationService.getUnreadCount(username));

        return response;
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Mise à jour des champs
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDepartment() != null) {
            existingUser.setDepartment(updatedUser.getDepartment());
        }
        if (updatedUser.getPosition() != null) {
            existingUser.setPosition(updatedUser.getPosition());
        }

        return userRepository.save(existingUser);
    }

    public User assignManager(Long userId, Long managerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found: " + managerId));

        user.setManager(manager);
        return userRepository.save(user);
    }
}*/



//commenté le 27/05/2025 a 12:15 am
/*package com.holidaymatrix.service;

import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    // Méthode existante
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Nouvelle méthode pour findById
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Méthode mise à jour pour l'inscription
    public User register(RegisterRequest req) {
        logger.info("Registering new user: {}", req.getUsername());

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", req.getUsername());
            throw new RuntimeException("Username already exists");
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // Encodage du mot de passe
        u.setRole(req.getRole() != null ? req.getRole() : "USER");

        // Définir les nouveaux champs
        u.setEmail(req.getEmail());
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setDepartment(req.getDepartment());
        u.setPosition(req.getPosition());

        return userRepository.save(u);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public UserDetailsResponse getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        UserDetailsResponse response = new UserDetailsResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDepartment(user.getDepartment());
        response.setPosition(user.getPosition());

        // Gestion du manager - adaptée selon votre modèle
        // Si votre modèle User a un champ manager
        /*
        if (user.getManager() != null) {
            response.setManagerId(user.getManager().getId());
            response.setManagerName(user.getManager().getUsername());
        }
        *

        // Alternative : récupérer le manager via EmployeeService
        // Cette partie sera gérée par l'AuthController

        // Nombre de notifications non lues
        try {
            response.setNotificationCount((int) notificationService.getUnreadCount(username));
        } catch (Exception e) {
            logger.warn("Erreur lors de la récupération des notifications pour {}: {}", username, e.getMessage());
            response.setNotificationCount(0);
        }

        return response;
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Mise à jour des champs
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDepartment() != null) {
            existingUser.setDepartment(updatedUser.getDepartment());
        }
        if (updatedUser.getPosition() != null) {
            existingUser.setPosition(updatedUser.getPosition());
        }

        return userRepository.save(existingUser);
    }

    // Méthode pour assigner un manager (si votre modèle User supporte cette relation)
    /*
    public User assignManager(Long userId, Long managerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found: " + managerId));

        user.setManager(manager);
        return userRepository.save(user);
    }
    *

    // Méthode pour supprimer un utilisateur
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    // Méthode pour obtenir les utilisateurs par département
    public List<User> getUsersByDepartment(String department) {
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment() != null &&
                        user.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Méthode pour obtenir tous les managers
    public List<User> getAllManagers() {
        return getUsersByRole("MANAGER");
    }

    // Méthode pour vérifier si un utilisateur existe
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Méthode pour vérifier si un email existe déjà
    public boolean emailExists(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email));
    }

    public User assignManager(Long userId, Long managerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found: " + managerId));

        user.setManager(manager);
        return userRepository.save(user);
    }

}*/

package com.holidaymatrix.service;

import com.holidaymatrix.dto.RegisterRequest;
import com.holidaymatrix.dto.UserDetailsResponse;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    // Méthode existante
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Nouvelle méthode pour findById
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Méthode mise à jour pour l'inscription
    public User register(RegisterRequest req) {
        logger.info("Registering new user: {}", req.getUsername());

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", req.getUsername());
            throw new RuntimeException("Username already exists");
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // Encodage du mot de passe
        u.setRole(req.getRole() != null ? req.getRole() : "USER");

        // Définir les nouveaux champs
        u.setEmail(req.getEmail());
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setDepartment(req.getDepartment());
        u.setPosition(req.getPosition());

        return userRepository.save(u);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public UserDetailsResponse getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        UserDetailsResponse response = new UserDetailsResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDepartment(user.getDepartment());
        response.setPosition(user.getPosition());

        // Gestion du manager - adaptée selon votre modèle
        // Si votre modèle User a un champ manager
        /*
        if (user.getManager() != null) {
            response.setManagerId(user.getManager().getId());
            response.setManagerName(user.getManager().getUsername());
        }
        */

        // Alternative : récupérer le manager via EmployeeService
        // Cette partie sera gérée par l'AuthController

        // Nombre de notifications non lues
        try {
            response.setNotificationCount((int) notificationService.getUnreadCount(username));
        } catch (Exception e) {
            logger.warn("Erreur lors de la récupération des notifications pour {}: {}", username, e.getMessage());
            response.setNotificationCount(0);
        }

        return response;
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Mise à jour des champs
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDepartment() != null) {
            existingUser.setDepartment(updatedUser.getDepartment());
        }
        if (updatedUser.getPosition() != null) {
            existingUser.setPosition(updatedUser.getPosition());
        }

        return userRepository.save(existingUser);
    }

    // Méthode pour assigner un manager (si votre modèle User supporte cette relation)

    public User assignManager(Long userId, Long managerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found: " + managerId));

        user.setManager(manager);
        return userRepository.save(user);
    }


    // Méthode pour supprimer un utilisateur
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    // Méthode pour obtenir les utilisateurs par département
    public List<User> getUsersByDepartment(String department) {
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment() != null &&
                        user.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Méthode pour obtenir tous les managers
    public List<User> getAllManagers() {
        return getUsersByRole("MANAGER");
    }

    // Méthode pour vérifier si un utilisateur existe
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Méthode pour vérifier si un email existe déjà
    public boolean emailExists(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email));
    }
}