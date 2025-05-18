package com.holidaymatrix.controller;

import com.holidaymatrix.dto.AnnualMatrixRequest;
import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.service.AnnualMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matrices")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnualMatrixController {
    @Autowired
    private AnnualMatrixService matrixService;

    @PostMapping
    public ResponseEntity<AnnualMatrix> createMatrix(
            Authentication authentication,
            @RequestBody AnnualMatrixRequest request) {

        AnnualMatrix matrix = matrixService.createMatrix(authentication.getName(), request);
        return new ResponseEntity<>(matrix, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<AnnualMatrix>> getMyMatrices(Authentication authentication) {
        List<AnnualMatrix> matrices = matrixService.getManagerMatrices(authentication.getName());
        return ResponseEntity.ok(matrices);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<AnnualMatrix>> getYearMatrices(@PathVariable int year) {
        List<AnnualMatrix> matrices = matrixService.getYearMatrices(year);
        return ResponseEntity.ok(matrices);
    }

    @PutMapping("/{id}/hos-validation")
    public ResponseEntity<AnnualMatrix> updateHosValidation(
            @PathVariable Long id,
            @RequestParam String status) {

        AnnualMatrix.ValidationStatus validationStatus = AnnualMatrix.ValidationStatus.valueOf(status.toUpperCase());
        AnnualMatrix matrix = matrixService.updateHosValidation(id, validationStatus);
        return ResponseEntity.ok(matrix);
    }

    @PutMapping("/{id}/dg-validation")
    public ResponseEntity<AnnualMatrix> updateDgValidation(
            @PathVariable Long id,
            @RequestParam String status) {

        AnnualMatrix.ValidationStatus validationStatus = AnnualMatrix.ValidationStatus.valueOf(status.toUpperCase());
        AnnualMatrix matrix = matrixService.updateDgValidation(id, validationStatus);
        return ResponseEntity.ok(matrix);
    }
}