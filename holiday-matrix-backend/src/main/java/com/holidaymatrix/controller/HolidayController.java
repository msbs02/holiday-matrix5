package com.holidaymatrix.controller;

import com.holidaymatrix.dto.HolidayRequest;
import com.holidaymatrix.model.Holiday;
import com.holidaymatrix.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @PostMapping
    public ResponseEntity<Holiday> createHoliday(
            Authentication authentication,
            @RequestBody HolidayRequest request) {

        Holiday holiday = holidayService.createHoliday(authentication.getName(), request);
        return new ResponseEntity<>(holiday, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Holiday>> getMyHolidays(Authentication authentication) {
        List<Holiday> holidays = holidayService.getUserHolidays(authentication.getName());
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/my/upcoming")
    public ResponseEntity<List<Holiday>> getMyUpcomingHolidays(Authentication authentication) {
        List<Holiday> holidays = holidayService.getUpcomingUserHolidays(authentication.getName());
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/team")
    public ResponseEntity<List<Holiday>> getTeamHolidays(Authentication authentication) {
        List<Holiday> holidays = holidayService.getTeamHolidays(authentication.getName());
        return ResponseEntity.ok(holidays);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Holiday> updateHolidayStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Holiday.HolidayStatus holidayStatus = Holiday.HolidayStatus.valueOf(status.toUpperCase());
        Holiday holiday = holidayService.updateHolidayStatus(id, holidayStatus);
        return ResponseEntity.ok(holiday);
    }
}