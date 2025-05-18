package com.holidaymatrix.service;

import com.holidaymatrix.dto.HolidayPeriodDTO;
import com.holidaymatrix.model.HolidayPeriod;
import com.holidaymatrix.repository.HolidayPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HolidayPeriodService {

    @Autowired
    private HolidayPeriodRepository holidayPeriodRepository;

    public List<HolidayPeriodDTO> getAllHolidayPeriods() {
        return holidayPeriodRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HolidayPeriodDTO> getHolidayPeriodById(Long id) {
        return holidayPeriodRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<HolidayPeriodDTO> getUpcomingHolidayPeriods() {
        LocalDate today = LocalDate.now();
        return holidayPeriodRepository.findByStartDateAfter(today).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HolidayPeriodDTO createHolidayPeriod(HolidayPeriodDTO dto) {
        HolidayPeriod period = new HolidayPeriod();
        period.setName(dto.getName());
        period.setStartDate(dto.getStartDate());
        period.setEndDate(dto.getEndDate());
        period.setNotificationSent(false);

        HolidayPeriod savedPeriod = holidayPeriodRepository.save(period);
        return convertToDTO(savedPeriod);
    }

    public Optional<HolidayPeriodDTO> updateHolidayPeriod(Long id, HolidayPeriodDTO dto) {
        return holidayPeriodRepository.findById(id)
                .map(period -> {
                    period.setName(dto.getName());
                    period.setStartDate(dto.getStartDate());
                    period.setEndDate(dto.getEndDate());

                    HolidayPeriod updatedPeriod = holidayPeriodRepository.save(period);
                    return convertToDTO(updatedPeriod);
                });
    }

    public Optional<HolidayPeriodDTO> markNotificationSent(Long id) {
        return holidayPeriodRepository.findById(id)
                .map(period -> {
                    period.setNotificationSent(true);
                    period.setNotificationDate(LocalDateTime.now());

                    HolidayPeriod updatedPeriod = holidayPeriodRepository.save(period);
                    return convertToDTO(updatedPeriod);
                });
    }

    public boolean deleteHolidayPeriod(Long id) {
        if (holidayPeriodRepository.existsById(id)) {
            holidayPeriodRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private HolidayPeriodDTO convertToDTO(HolidayPeriod period) {
        HolidayPeriodDTO dto = new HolidayPeriodDTO();
        dto.setId(period.getId());
        dto.setName(period.getName());
        dto.setStartDate(period.getStartDate());
        dto.setEndDate(period.getEndDate());
        return dto;
    }
}