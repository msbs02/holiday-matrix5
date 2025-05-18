package com.holidaymatrix.controller;

import com.holidaymatrix.dto.PositionDTO;
import com.holidaymatrix.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "http://localhost:4200")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(positionService.getPositionsByOrganization(organizationId));
    }

    @GetMapping("/critical")
    public ResponseEntity<List<PositionDTO>> getCriticalPositions() {
        return ResponseEntity.ok(positionService.getCriticalPositions());
    }

    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO dto) {
        return positionService.createPosition(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDTO> updatePosition(
            @PathVariable Long id,
            @RequestBody PositionDTO dto) {
        return positionService.updatePosition(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        if (positionService.deletePosition(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}