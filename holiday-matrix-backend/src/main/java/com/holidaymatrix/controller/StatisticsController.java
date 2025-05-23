/*package com.holidaymatrix.controller;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/managers")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<List<String>> getAllManagers() {
        return ResponseEntity.ok(statisticsService.getAllManagerNames());
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<ManagerStatisticsDTO> getManagerStatistics(
            @PathVariable String managerId) {
        return ResponseEntity.ok(statisticsService.getManagerStatistics(managerId));
    }
}*/


//make en comment le 23/05/2025 a 1:27
/*
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Récupère la liste de tous les managers
     *
    @GetMapping("/managers")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<List<String>> getAllManagers() {
        try {
            List<String> managers = statisticsService.getAllManagerNames();
            return ResponseEntity.ok(managers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques d'un manager spécifique par son nom
     *
    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<ManagerStatisticsDTO> getManagerStatistics(
            @RequestParam String managerName) {
        try {
            ManagerStatisticsDTO stats = statisticsService.getManagerStatistics(managerName);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques d'un manager spécifique par son ID
     *
    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<ManagerStatisticsDTO> getManagerStatisticsById(
            @PathVariable Long managerId) {
        try {
            ManagerStatisticsDTO stats = statisticsService.getManagerStatisticsById(managerId);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques globales pour HOS et DG
     *
    @GetMapping("/global")
    @PreAuthorize("hasAnyRole('HOS', 'DG')")
    public ResponseEntity<ManagerStatisticsDTO> getGlobalStatistics() {
        try {
            ManagerStatisticsDTO stats = statisticsService.getGlobalStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques de tous les managers avec des plannings
     *
    @GetMapping("/all-managers")
    @PreAuthorize("hasAnyRole('HOS', 'DG')")
    public ResponseEntity<List<ManagerStatisticsDTO>> getAllManagersStatistics() {
        try {
            List<ManagerStatisticsDTO> stats = statisticsService.getAllManagersWithStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère le nombre d'employés par manager
     *
    @GetMapping("/employee-count")
    @PreAuthorize("hasAnyRole('MANAGER', 'HOS', 'DG')")
    public ResponseEntity<Map<String, Integer>> getEmployeeCountByManager() {
        try {
            Map<String, Integer> counts = statisticsService.getEmployeeCountByManager();
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}*/
package com.holidaymatrix.controller;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Récupère la liste de tous les managers
     */
    @GetMapping("/managers")
    @PreAuthorize("hasAnyRole('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<List<String>> getAllManagers() {
        try {
            List<String> managers = statisticsService.getAllManagerNames();
            return ResponseEntity.ok(managers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques d'un manager spécifique par son nom
     */
    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<ManagerStatisticsDTO> getManagerStatistics(
            @RequestParam String managerName) {
        try {
            ManagerStatisticsDTO stats = statisticsService.getManagerStatistics(managerName);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques d'un manager spécifique par son ID
     */
    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<ManagerStatisticsDTO> getManagerStatisticsById(
            @PathVariable Long managerId) {
        try {
            ManagerStatisticsDTO stats = statisticsService.getManagerStatisticsById(managerId);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques globales pour HEAD_OF_SERVICE et DIRECTION_GENERAL
     */
    @GetMapping("/global")
    @PreAuthorize("hasAnyRole('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<ManagerStatisticsDTO> getGlobalStatistics() {
        try {
            ManagerStatisticsDTO stats = statisticsService.getGlobalStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère les statistiques de tous les managers avec des plannings
     */
    @GetMapping("/all-managers")
    @PreAuthorize("hasAnyRole('HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<List<ManagerStatisticsDTO>> getAllManagersStatistics() {
        try {
            List<ManagerStatisticsDTO> stats = statisticsService.getAllManagersWithStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupère le nombre d'employés par manager
     */
    @GetMapping("/employee-count")
    @PreAuthorize("hasAnyRole('MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL')")
    public ResponseEntity<Map<String, Integer>> getEmployeeCountByManager() {
        try {
            Map<String, Integer> counts = statisticsService.getEmployeeCountByManager();
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}