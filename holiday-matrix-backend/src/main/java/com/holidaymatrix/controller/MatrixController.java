package com.holidaymatrix.controller;

import com.holidaymatrix.dto.MatrixDTO;
import com.holidaymatrix.model.User;
import java.util.Optional;
import com.holidaymatrix.dto.MatrixEntryDTO;
import com.holidaymatrix.repository.UserRepository;
import com.holidaymatrix.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matrices-v2")
@CrossOrigin(origins = "http://localhost:4200")
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    @GetMapping
    public ResponseEntity<List<MatrixDTO>> getAllMatrices() {
        return ResponseEntity.ok(matrixService.getAllMatrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrixDTO> getMatrixById(@PathVariable Long id) {
        return matrixService.getMatrixById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<MatrixDTO> getMatrixByYear(@PathVariable Integer year) {
        return matrixService.getMatrixByYear(year)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@PostMapping
    public ResponseEntity<MatrixDTO> createMatrix(
            @RequestBody MatrixDTO dto,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return matrixService.createMatrix(dto, userId)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }*/
    @Autowired
    private UserRepository userRepository;
    // Dans MatrixController.java
    @PostMapping
    public ResponseEntity<MatrixDTO> createMatrix(
            @RequestBody MatrixDTO dto,
            Authentication authentication) {

        // Au lieu de ceci qui cause l'erreur:
        // Long userId = Long.parseLong(authentication.getName());

        // Faites ceci à la place:
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Long userId = userOptional.get().getId();

        return matrixService.createMatrix(dto, userId)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/{matrixId}/entries")
    public ResponseEntity<MatrixEntryDTO> addMatrixEntry(
            @PathVariable Long matrixId,
            @RequestBody MatrixEntryDTO dto) {
        return matrixService.addMatrixEntry(matrixId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    /*@PostMapping("/{matrixId}/validate/hos")
    public ResponseEntity<MatrixDTO> validateMatrixByHOS(
            @PathVariable Long matrixId,
            Authentication authentication) {
        Long hosId = Long.parseLong(authentication.getName());
        return matrixService.validateMatrixByHOS(matrixId, hosId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{matrixId}/validate/dg")
    public ResponseEntity<MatrixDTO> validateMatrixByDG(
            @PathVariable Long matrixId,
            Authentication authentication) {
        Long dgId = Long.parseLong(authentication.getName());
        return matrixService.validateMatrixByDG(matrixId, dgId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatrix(@PathVariable Long id) {
        if (matrixService.deleteMatrix(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{matrixId}/validate/hos")
    public ResponseEntity<MatrixDTO> validateMatrixByHOS(
            @PathVariable Long matrixId,
            Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Long hosId = userOptional.get().getId();

        return matrixService.validateMatrixByHOS(matrixId, hosId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{matrixId}/validate/dg")
    public ResponseEntity<MatrixDTO> validateMatrixByDG(
            @PathVariable Long matrixId,
            Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Long dgId = userOptional.get().getId();

        return matrixService.validateMatrixByDG(matrixId, dgId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}