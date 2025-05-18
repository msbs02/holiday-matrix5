package com.holidaymatrix.controller;

import com.holidaymatrix.dto.EmployeeDashboardData;
import com.holidaymatrix.dto.HolidayPlanningDTO;
import com.holidaymatrix.dto.ManagerDashboardData;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.UserRepository;
import com.holidaymatrix.service.HolidayPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPlanningService planningService;

    // Endpoint pour le dashboard employé
    /*@GetMapping("/employee")
    public ResponseEntity<EmployeeDashboardData> getEmployeeDashboard(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        Long userId = Long.parseLong(authentication.getName());
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        User user = userOptional.get();
        Optional<Employee> employeeOptional = employeeRepository.findByUser(user);
        */
    @GetMapping("/employee")
    public ResponseEntity<EmployeeDashboardData> getEmployeeDashboard(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        User user = userOptional.get();
        Optional<Employee> employeeOptional = employeeRepository.findByUser(user);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Employee employee = employeeOptional.get();

        // Construction des données du dashboard
        EmployeeDashboardData data = new EmployeeDashboardData();
        data.setCompanyName("LEONI");

        // Configuration du calendrier
        EmployeeDashboardData.CalendarData calendar = new EmployeeDashboardData.CalendarData();
        calendar.setMonth(LocalDate.now().getMonth().toString());
        calendar.setYear(LocalDate.now().getYear());
        calendar.setHighlightedDate(LocalDate.now().getDayOfMonth());

        // Exemple de jours fériés (à remplacer par de vraies données)
        calendar.setHolidayDates(Arrays.asList(1, 15, 25));
        data.setCalendar(calendar);

        // Exemple de notifications (à remplacer par de vraies données)
        EmployeeDashboardData.Notification notif = new EmployeeDashboardData.Notification();
        notif.setMessage("Vos congés pour la période d'été ont été validés.");
        notif.setRead(false);
        data.setNotifications(Arrays.asList(notif));

        // Exemple de news (à remplacer par de vraies données)
        EmployeeDashboardData.NewsItem news = new EmployeeDashboardData.NewsItem();
        news.setTitle("Fermeture annuelle");
        news.setContent("L'entreprise sera fermée du 1er au 15 août. Planifiez vos congés en conséquence.");
        data.setNews(Arrays.asList(news));

        return ResponseEntity.ok(data);
    }

    // Endpoint pour le dashboard manager
    @GetMapping("/manager")
    public ResponseEntity<ManagerDashboardData> getManagerDashboard(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        //Long userId = Long.parseLong(authentication.getName());
        //Optional<User> userOptional = userRepository.findById(userId);

        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);


        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        User user = userOptional.get();
        Optional<Employee> employeeOptional = employeeRepository.findByUser(user);

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Employee employee = employeeOptional.get();

        // Construction des données du dashboard
        ManagerDashboardData data = new ManagerDashboardData();
        data.setManagerName(employee.getName());
        data.setWelcomeMessage("Bienvenue sur votre tableau de bord, " + employee.getName());

        // Recherche des employés dont l'utilisateur est le manager
        List<Employee> managedEmployees = employeeRepository.findByDirectManager(employee);

        // Récupération des plannings en attente pour les employés gérés
        List<HolidayPlanningDTO> pendingPlannings = managedEmployees.stream()
                .flatMap(managedEmployee -> planningService.getPlanningsByEmployee(managedEmployee.getId()).stream())
                .filter(planning -> !planning.isManagerValidated())
                .toList();

        // Conversion des plannings en demandes de congé pour le dashboard
        List<ManagerDashboardData.LeaveRequest> leaveRequests = pendingPlannings.stream()
                .map(planning -> {
                    ManagerDashboardData.LeaveRequest req = new ManagerDashboardData.LeaveRequest();
                    req.setTeamOrPosition(planning.getEmployeeName() + " - Position");
                    req.setLeaveCategory("Congé");
                    req.setEmployeeName(planning.getEmployeeName());
                    req.setStatus("En attente");
                    return req;
                })
                .toList();

        data.setLeaveRequests(leaveRequests.isEmpty() ?
                Arrays.asList(createEmptyLeaveRequest()) : // Exemple de donnée si pas de demandes réelles
                leaveRequests);

        return ResponseEntity.ok(data);
    }

    // Endpoint pour le dashboard HOS (Head of Service)
    @GetMapping("/hos")
    public ResponseEntity<ManagerDashboardData> getHOSDashboard(Authentication authentication) {
        // Similaire au dashboard manager, mais avec des permissions élargies
        // et des données spécifiques au HOS
        return getManagerDashboard(authentication);
    }

    // Endpoint pour le dashboard DG (Direction Générale)
    @GetMapping("/dg")
    public ResponseEntity<ManagerDashboardData> getDGDashboard(Authentication authentication) {
        // Similaire au dashboard manager, mais avec des permissions complètes
        // et des données spécifiques à la DG
        return getManagerDashboard(authentication);
    }

    private ManagerDashboardData.LeaveRequest createEmptyLeaveRequest() {
        ManagerDashboardData.LeaveRequest req = new ManagerDashboardData.LeaveRequest();
        req.setTeamOrPosition("Équipe IT");
        req.setLeaveCategory("Congé payé");
        req.setEmployeeName("Alice Martin");
        req.setStatus("En attente");
        return req;
    }
}