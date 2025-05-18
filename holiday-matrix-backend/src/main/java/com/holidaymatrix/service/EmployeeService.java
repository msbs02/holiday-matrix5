package com.holidaymatrix.service;

import com.holidaymatrix.dto.EmployeeDTO;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.Position;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.PositionRepository;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<EmployeeDTO> getEmployeeByUser(Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> employeeRepository.findByUser(user))
                .map(this::convertToDTO);
    }

    public List<EmployeeDTO> getEmployeesByManager(Long managerId) {
        return employeeRepository.findById(managerId)
                .map(manager -> employeeRepository.findByDirectManager(manager).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public Optional<EmployeeDTO> createEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setName(dto.getName());

        // Associer la position
        if (dto.getPositionId() != null) {
            Optional<Position> position = positionRepository.findById(dto.getPositionId());
            position.ifPresent(employee::setPosition);
        }

        // Associer l'utilisateur
        if (dto.getUserId() != null) {
            Optional<User> user = userRepository.findById(dto.getUserId());
            user.ifPresent(employee::setUser);
        }

        // Associer le manager direct
        if (dto.getDirectManagerId() != null) {
            Optional<Employee> directManager = employeeRepository.findById(dto.getDirectManagerId());
            directManager.ifPresent(employee::setDirectManager);
        }

        // Associer le manager de niveau supérieur
        if (dto.getNextLevelManagerId() != null) {
            Optional<Employee> nextLevelManager = employeeRepository.findById(dto.getNextLevelManagerId());
            nextLevelManager.ifPresent(employee::setNextLevelManager);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return Optional.of(convertToDTO(savedEmployee));
    }

    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO dto) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setEmployeeId(dto.getEmployeeId());
                    employee.setName(dto.getName());

                    // Mettre à jour la position
                    if (dto.getPositionId() != null) {
                        positionRepository.findById(dto.getPositionId())
                                .ifPresent(employee::setPosition);
                    }

                    // Mettre à jour l'utilisateur
                    if (dto.getUserId() != null) {
                        userRepository.findById(dto.getUserId())
                                .ifPresent(employee::setUser);
                    }

                    // Mettre à jour le manager direct
                    if (dto.getDirectManagerId() != null) {
                        employeeRepository.findById(dto.getDirectManagerId())
                                .ifPresent(employee::setDirectManager);
                    }

                    // Mettre à jour le manager de niveau supérieur
                    if (dto.getNextLevelManagerId() != null) {
                        employeeRepository.findById(dto.getNextLevelManagerId())
                                .ifPresent(employee::setNextLevelManager);
                    }

                    Employee updatedEmployee = employeeRepository.save(employee);
                    return convertToDTO(updatedEmployee);
                });
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());

        if (employee.getPosition() != null) {
            dto.setPositionId(employee.getPosition().getId());
            dto.setPositionTitle(employee.getPosition().getTitle());
        }

        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getId());
            dto.setUsername(employee.getUser().getUsername());
        }

        if (employee.getDirectManager() != null) {
            dto.setDirectManagerId(employee.getDirectManager().getId());
            dto.setDirectManagerName(employee.getDirectManager().getName());
        }

        if (employee.getNextLevelManager() != null) {
            dto.setNextLevelManagerId(employee.getNextLevelManager().getId());
            dto.setNextLevelManagerName(employee.getNextLevelManager().getName());
        }

        return dto;
    }
}