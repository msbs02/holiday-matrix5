//commenté le 27/05/2025 a 12:06 am
/*
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

import java.util.ArrayList;
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

    // Méthode utilitaire pour trouver un employé par ID utilisateur (simplifié)
    /*public Optional<Employee> findByUserId(Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> employeeRepository.findByUser(user));
    }*/

    // Méthode pour obtenir les employés sous la responsabilité d'un manager (par ID utilisateur du manager)
    /*public List<EmployeeDTO> getEmployeesByManagerUserId(Long managerUserId) {
        return getEmployeeByUser(managerUserId)
                .map(managerEmployee -> {
                    // Rechercher par l'ID de l'employé manager, pas par l'ID utilisateur
                    return employeeRepository.findById(managerEmployee.getId())
                            .map(manager -> employeeRepository.findByDirectManager(manager).stream()
                                    .map(this::convertToDTO)
                                    .collect(Collectors.toList()))
                            .orElse(List.of());
                })
                .orElse(List.of());
    }*/

    // Méthode pour vérifier si un utilisateur est manager d'un autre employé
    /*public boolean isManagerOf(Long managerUserId, Long employeeUserId) {
        Optional<EmployeeDTO> employee = getEmployeeByUser(employeeUserId);
        Optional<EmployeeDTO> manager = getEmployeeByUser(managerUserId);

        if (employee.isPresent() && manager.isPresent()) {
            return manager.get().getId().equals(employee.get().getDirectManagerId()) ||
                    manager.get().getId().equals(employee.get().getNextLevelManagerId());
        }
        return false;
    }*/

    // Méthode pour obtenir la hiérarchie complète d'un employé
    /*public List<EmployeeDTO> getEmployeeHierarchy(Long employeeUserId) {
        List<EmployeeDTO> hierarchy = new ArrayList<>();
        Optional<EmployeeDTO> employee = getEmployeeByUser(employeeUserId);

        if (employee.isPresent()) {
            hierarchy.add(employee.get());

            // Ajouter le manager direct
            if (employee.get().getDirectManagerId() != null) {
                employeeRepository.findById(employee.get().getDirectManagerId())
                        .map(this::convertToDTO)
                        .ifPresent(hierarchy::add);
            }

            // Ajouter le manager de niveau supérieur
            if (employee.get().getNextLevelManagerId() != null) {
                employeeRepository.findById(employee.get().getNextLevelManagerId())
                        .map(this::convertToDTO)
                        .ifPresent(hierarchy::add);
            }
        }

        return hierarchy;
    }*



    // Méthode utilitaire pour trouver un employé par ID utilisateur (simplifié)
    public Optional<Employee> findByUserId(Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> employeeRepository.findByUser(user));
    }

    // Méthode pour obtenir les employés sous la responsabilité d'un manager (par ID utilisateur du manager)
    public List<EmployeeDTO> getEmployeesByManagerUserId(Long managerUserId) {
        // D'abord, trouver l'employé correspondant au manager
        Optional<EmployeeDTO> managerEmployee = getEmployeeByUser(managerUserId);

        if (managerEmployee.isPresent()) {
            Long managerEmployeeId = managerEmployee.get().getId();

            // Trouver tous les employés qui ont ce manager comme directManager
            return employeeRepository.findAll().stream()
                    .filter(emp -> emp.getDirectManager() != null &&
                            emp.getDirectManager().getId().equals(managerEmployeeId))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return List.of(); // Retourner une liste vide si le manager n'est pas trouvé
    }

    // Méthode pour vérifier si un utilisateur est manager d'un autre employé
    public boolean isManagerOf(Long managerUserId, Long employeeUserId) {
        Optional<EmployeeDTO> employee = getEmployeeByUser(employeeUserId);
        Optional<EmployeeDTO> manager = getEmployeeByUser(managerUserId);

        if (employee.isPresent() && manager.isPresent()) {
            return manager.get().getId().equals(employee.get().getDirectManagerId()) ||
                    manager.get().getId().equals(employee.get().getNextLevelManagerId());
        }
        return false;
    }

    // Méthode pour obtenir la hiérarchie complète d'un employé
    public List<EmployeeDTO> getEmployeeHierarchy(Long employeeUserId) {
        List<EmployeeDTO> hierarchy = new ArrayList<>();
        Optional<EmployeeDTO> employee = getEmployeeByUser(employeeUserId);

        if (employee.isPresent()) {
            hierarchy.add(employee.get());

            // Ajouter le manager direct
            if (employee.get().getDirectManagerId() != null) {
                employeeRepository.findById(employee.get().getDirectManagerId())
                        .map(this::convertToDTO)
                        .ifPresent(hierarchy::add);
            }

            // Ajouter le manager de niveau supérieur
            if (employee.get().getNextLevelManagerId() != null) {
                employeeRepository.findById(employee.get().getNextLevelManagerId())
                        .map(this::convertToDTO)
                        .ifPresent(hierarchy::add);
            }
        }

        return hierarchy;
    }


}*/



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
import java.util.ArrayList;

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

    // Méthode pour trouver directement l'entity Employee
    public Optional<Employee> findByUserId(Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> employeeRepository.findByUser(user));
    }

    // Méthode pour obtenir les employés sous un manager (par ID utilisateur du manager)
    public List<EmployeeDTO> getEmployeesByManagerUserId(Long managerUserId) {
        try {
            // Trouver l'employé correspondant au manager
            Optional<Employee> managerEmployee = findByUserId(managerUserId);

            if (managerEmployee.isPresent()) {
                Long managerEmployeeId = managerEmployee.get().getId();

                // Trouver tous les employés qui ont ce manager comme directManager
                return employeeRepository.findAll().stream()
                        .filter(emp -> emp.getDirectManager() != null &&
                                emp.getDirectManager().getId().equals(managerEmployeeId))
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }

            return new ArrayList<>(); // Retourner une liste vide si le manager n'est pas trouvé
        } catch (Exception e) {
            System.err.println("Erreur dans getEmployeesByManagerUserId: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Méthode pour vérifier si un utilisateur est manager d'un autre employé
    public boolean isManagerOf(Long managerUserId, Long employeeUserId) {
        try {
            Optional<Employee> employee = findByUserId(employeeUserId);
            Optional<Employee> manager = findByUserId(managerUserId);

            if (employee.isPresent() && manager.isPresent()) {
                Employee emp = employee.get();
                Employee mgr = manager.get();

                // Vérifier si le manager est le directManager ou nextLevelManager
                return (emp.getDirectManager() != null && emp.getDirectManager().getId().equals(mgr.getId())) ||
                        (emp.getNextLevelManager() != null && emp.getNextLevelManager().getId().equals(mgr.getId()));
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erreur dans isManagerOf: " + e.getMessage());
            return false;
        }
    }

    // Méthode pour obtenir la hiérarchie complète d'un employé
    public List<EmployeeDTO> getEmployeeHierarchy(Long employeeUserId) {
        List<EmployeeDTO> hierarchy = new ArrayList<>();
        try {
            Optional<Employee> employee = findByUserId(employeeUserId);

            if (employee.isPresent()) {
                hierarchy.add(convertToDTO(employee.get()));

                // Ajouter le manager direct
                if (employee.get().getDirectManager() != null) {
                    hierarchy.add(convertToDTO(employee.get().getDirectManager()));
                }

                // Ajouter le manager de niveau supérieur
                if (employee.get().getNextLevelManager() != null) {
                    hierarchy.add(convertToDTO(employee.get().getNextLevelManager()));
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans getEmployeeHierarchy: " + e.getMessage());
        }

        return hierarchy;
    }

    // Méthodes CRUD
    public Optional<EmployeeDTO> createEmployee(EmployeeDTO dto) {
        try {
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
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'employé: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO dto) {
        try {
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
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'employé: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deleteEmployee(Long id) {
        try {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'employé: " + e.getMessage());
            return false;
        }
    }

    // Méthode de conversion DTO
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

    // Méthodes utilitaires supplémentaires
    public List<EmployeeDTO> getEmployeesByPosition(String positionTitle) {
        return employeeRepository.findAll().stream()
                .filter(emp -> emp.getPosition() != null &&
                        emp.getPosition().getTitle().equalsIgnoreCase(positionTitle))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        return employeeRepository.findAll().stream()
                .filter(emp -> emp.getUser() != null &&
                        emp.getUser().getDepartment() != null &&
                        emp.getUser().getDepartment().equalsIgnoreCase(department))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public long countEmployeesByManager(Long managerUserId) {
        return getEmployeesByManagerUserId(managerUserId).size();
    }
}