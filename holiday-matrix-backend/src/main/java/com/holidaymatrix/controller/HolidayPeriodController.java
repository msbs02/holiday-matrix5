package com.holidaymatrix.controller;

import com.holidaymatrix.dto.HolidayPeriodDTO;
import com.holidaymatrix.service.HolidayPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holiday-periods")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayPeriodController {

    @Autowired
    private HolidayPeriodService holidayPeriodService;

    @GetMapping
    public ResponseEntity<List<HolidayPeriodDTO>> getAllHolidayPeriods() {
        return ResponseEntity.ok(holidayPeriodService.getAllHolidayPeriods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolidayPeriodDTO> getHolidayPeriodById(@PathVariable Long id) {
        return holidayPeriodService.getHolidayPeriodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<HolidayPeriodDTO>> getUpcomingHolidayPeriods() {
        return ResponseEntity.ok(holidayPeriodService.getUpcomingHolidayPeriods());
    }

    @PostMapping
    public ResponseEntity<HolidayPeriodDTO> createHolidayPeriod(@RequestBody HolidayPeriodDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(holidayPeriodService.createHolidayPeriod(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayPeriodDTO> updateHolidayPeriod(
            @PathVariable Long id,
            @RequestBody HolidayPeriodDTO dto) {
        return holidayPeriodService.updateHolidayPeriod(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/notify")
    public ResponseEntity<HolidayPeriodDTO> markNotificationSent(@PathVariable Long id) {
        return holidayPeriodService.markNotificationSent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolidayPeriod(@PathVariable Long id) {
        if (holidayPeriodService.deleteHolidayPeriod(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}