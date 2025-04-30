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

package com.holidaymatrix.service;

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
}