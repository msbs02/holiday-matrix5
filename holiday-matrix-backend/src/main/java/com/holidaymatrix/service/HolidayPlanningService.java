package com.holidaymatrix.service;

import com.holidaymatrix.dto.HolidayPlanningDTO;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.HolidayPeriod;
import com.holidaymatrix.model.HolidayPlanning;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.HolidayPeriodRepository;
import com.holidaymatrix.repository.HolidayPlanningRepository;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HolidayPlanningService {

    @Autowired
    private HolidayPlanningRepository planningRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPeriodRepository periodRepository;

    @Autowired
    private UserRepository userRepository;

    public List<HolidayPlanningDTO> getAllPlannings() {
        return planningRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HolidayPlanningDTO> getPlanningById(Long id) {
        return planningRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<HolidayPlanningDTO> getPlanningsByEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> planningRepository.findByEmployee(employee).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<HolidayPlanningDTO> getPlanningsByPeriod(Long periodId) {
        return periodRepository.findById(periodId)
                .map(period -> planningRepository.findByHolidayPeriod(period).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Transactional
    public Optional<HolidayPlanningDTO> createPlanning(HolidayPlanningDTO dto, Long createdById) {
        return userRepository.findById(createdById)
                .flatMap(creator -> employeeRepository.findById(dto.getEmployeeId())
                        .flatMap(employee -> periodRepository.findById(dto.getHolidayPeriodId())
                                .map(period -> {
                                    // Vérifier si un planning existe déjà pour cet employé et cette période
                                    List<HolidayPlanning> existingPlannings = planningRepository.findByEmployeeAndHolidayPeriod(employee, period);

                                    if (!existingPlannings.isEmpty()) {
                                        // Mise à jour du planning existant
                                        HolidayPlanning planning = existingPlannings.get(0);
                                        planning.setStatus(dto.getStatus());
                                        planning.setComment(dto.getComment());
                                        planning.setUpdatedBy(creator);
                                        planning.setUpdatedAt(LocalDateTime.now());

                                        HolidayPlanning updatedPlanning = planningRepository.save(planning);
                                        return convertToDTO(updatedPlanning);
                                    } else {
                                        // Création d'un nouveau planning
                                        HolidayPlanning planning = new HolidayPlanning();
                                        planning.setEmployee(employee);
                                        planning.setHolidayPeriod(period);
                                        planning.setStatus(dto.getStatus());
                                        planning.setComment(dto.getComment());
                                        planning.setCreatedBy(creator);
                                        planning.setCreatedAt(LocalDateTime.now());

                                        HolidayPlanning savedPlanning = planningRepository.save(planning);
                                        return convertToDTO(savedPlanning);
                                    }
                                })));
    }

    @Transactional
    public Optional<HolidayPlanningDTO> validatePlanningByManager(Long planningId, Long managerId) {
        return userRepository.findById(managerId)
                .flatMap(manager -> planningRepository.findById(planningId)
                        .map(planning -> {
                            planning.setManagerValidated(true);
                            planning.setManagerValidatedBy(manager);
                            planning.setManagerValidatedAt(LocalDateTime.now());

                            HolidayPlanning validatedPlanning = planningRepository.save(planning);
                            return convertToDTO(validatedPlanning);
                        }));
    }

    @Transactional
    public Optional<HolidayPlanningDTO> validatePlanningByHOS(Long planningId, Long hosId) {
        return userRepository.findById(hosId)
                .flatMap(hos -> planningRepository.findById(planningId)
                        .map(planning -> {
                            planning.setHosValidated(true);
                            planning.setHosValidatedBy(hos);
                            planning.setHosValidatedAt(LocalDateTime.now());

                            HolidayPlanning validatedPlanning = planningRepository.save(planning);
                            return convertToDTO(validatedPlanning);
                        }));
    }

    @Transactional
    public Optional<HolidayPlanningDTO> validatePlanningByDG(Long planningId, Long dgId) {
        return userRepository.findById(dgId)
                .flatMap(dg -> planningRepository.findById(planningId)
                        .map(planning -> {
                            planning.setDgValidated(true);
                            planning.setDgValidatedBy(dg);
                            planning.setDgValidatedAt(LocalDateTime.now());

                            HolidayPlanning validatedPlanning = planningRepository.save(planning);
                            return convertToDTO(validatedPlanning);
                        }));
    }

    public boolean deletePlanning(Long id) {
        if (planningRepository.existsById(id)) {
            planningRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private HolidayPlanningDTO convertToDTO(HolidayPlanning planning) {
        HolidayPlanningDTO dto = new HolidayPlanningDTO();
        dto.setId(planning.getId());

        if (planning.getEmployee() != null) {
            dto.setEmployeeId(planning.getEmployee().getId());
            dto.setEmployeeName(planning.getEmployee().getName());
        }

        if (planning.getHolidayPeriod() != null) {
            dto.setHolidayPeriodId(planning.getHolidayPeriod().getId());
            dto.setHolidayPeriodName(planning.getHolidayPeriod().getName());
        }

        dto.setStatus(planning.getStatus());
        dto.setComment(planning.getComment());
        dto.setManagerValidated(planning.isManagerValidated());
        dto.setHosValidated(planning.isHosValidated());
        dto.setDgValidated(planning.isDgValidated());

        return dto;
    }
}