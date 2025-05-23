package com.holidaymatrix.controller;

import com.holidaymatrix.dto.HolidayPlanningDTO;
import com.holidaymatrix.service.HolidayPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.holidaymatrix.model.User;
import java.util.Optional;
import com.holidaymatrix.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/holiday-planning")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayPlanningController {

    @Autowired
    private HolidayPlanningService planningService;

    @GetMapping
    public ResponseEntity<List<HolidayPlanningDTO>> getAllPlannings() {
        return ResponseEntity.ok(planningService.getAllPlannings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolidayPlanningDTO> getPlanningById(@PathVariable Long id) {
        return planningService.getPlanningById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<HolidayPlanningDTO>> getPlanningsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(planningService.getPlanningsByEmployee(employeeId));
    }

    @GetMapping("/period/{periodId}")
    public ResponseEntity<List<HolidayPlanningDTO>> getPlanningsByPeriod(@PathVariable Long periodId) {
        return ResponseEntity.ok(planningService.getPlanningsByPeriod(periodId));
    }

    /*@PostMapping
    public ResponseEntity<HolidayPlanningDTO> createPlanning(
            @RequestBody HolidayPlanningDTO dto,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return planningService.createPlanning(dto, userId)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }*/
    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<HolidayPlanningDTO> createPlanning(
            @RequestBody HolidayPlanningDTO dto,
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

        return planningService.createPlanning(dto, userId)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    // Ajoutez cette annotation pour autoriser les requêtes de validation
    @PreAuthorize("hasRole('MANAGER') or hasRole('HEAD_OF_SERVICE') or hasRole('DIRECTION_GENERAL')")
    //@PostMapping("/{id}/validate/manager")
    @PostMapping("/{planningId}/validate/manager")
    public ResponseEntity<HolidayPlanningDTO> validatePlanningByManager(
            @PathVariable Long planningId,
            Authentication authentication) {
        Long managerId = Long.parseLong(authentication.getName());
        return planningService.validatePlanningByManager(planningId, managerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{planningId}/validate/hos")
    public ResponseEntity<HolidayPlanningDTO> validatePlanningByHOS(
            @PathVariable Long planningId,
            Authentication authentication) {
        Long hosId = Long.parseLong(authentication.getName());
        return planningService.validatePlanningByHOS(planningId, hosId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{planningId}/validate/dg")
    public ResponseEntity<HolidayPlanningDTO> validatePlanningByDG(
            @PathVariable Long planningId,
            Authentication authentication) {
        Long dgId = Long.parseLong(authentication.getName());
        return planningService.validatePlanningByDG(planningId, dgId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        if (planningService.deletePlanning(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}