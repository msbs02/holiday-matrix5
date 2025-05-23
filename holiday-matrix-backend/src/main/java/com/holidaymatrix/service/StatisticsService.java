/*package com.holidaymatrix.service;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.dto.WeeklyStatisticsDTO;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.HolidayPlanning;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.HolidayPlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPlanningRepository holidayPlanningRepository;

    public List<String> getAllManagerNames() {
        return employeeRepository.findAllManagers();
    }

    public ManagerStatisticsDTO getManagerStatistics(String managerId) {
        Long managerLongId = Long.parseLong(managerId);

        Employee manager = employeeRepository.findById(managerLongId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        List<Employee> employees = employeeRepository.findByManager(managerId);


        List<Long> employeeIds = employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        //verfier chatgpt

        List<HolidayPlanning> plannings = holidayPlanningRepository
                .findByEmployeeIdIn(employeeIds);


        // Grouper par semaine
        Map<Integer, Long> holidaysByWeek = plannings.stream()
                .collect(Collectors.groupingBy(
                        HolidayPlanning::getWeekNumber, Collectors.counting()
                ));

        // Convertir en liste de WeeklyStatisticsDTO
        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 7; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        // Créer le DTO final
        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId(managerId);
        result.setManagerName(manager.getName());
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }
}*/

// make en comment le 23/05/2025 a 1:15
/*
package com.holidaymatrix.service;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.dto.WeeklyStatisticsDTO;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.HolidayPlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPlanningRepository holidayPlanningRepository;

    public List<String> getAllManagerNames() {
        return employeeRepository.findAllManagers();
    }

    public ManagerStatisticsDTO getManagerStatistics(String managerId) {
        Long managerLongId = Long.parseLong(managerId);

        Employee manager = employeeRepository.findById(managerLongId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        // Utiliser l'année courante ou permettre de la passer en paramètre
        int currentYear = LocalDate.now().getYear();

        // Utiliser la méthode du repository pour récupérer les statistiques par semaine
        List<Object[]> weeklyData = holidayPlanningRepository
                .countPlanningsByWeekForManager(managerLongId, currentYear);

        // Créer une map pour faciliter la manipulation des données
        Map<Integer, Long> holidaysByWeek = new HashMap<>();
        for (Object[] row : weeklyData) {
            Integer weekNumber = (Integer) row[0];
            Long count = (Long) row[1];
            holidaysByWeek.put(weekNumber, count);
        }

        // Convertir en liste de WeeklyStatisticsDTO (52 semaines dans l'année)
        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        // Créer le DTO final
        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId(managerId);
        result.setManagerName(manager.getName());
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }

    /**
     * Méthode pour obtenir les statistiques globales (pour HOS et DG)
     *
    public ManagerStatisticsDTO getGlobalStatistics() {
        int currentYear = LocalDate.now().getYear();

        List<Object[]> weeklyData = holidayPlanningRepository
                .countPlanningsByWeekGlobal(currentYear);

        Map<Integer, Long> holidaysByWeek = new HashMap<>();
        for (Object[] row : weeklyData) {
            Integer weekNumber = (Integer) row[0];
            Long count = (Long) row[1];
            holidaysByWeek.put(weekNumber, count);
        }

        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId("GLOBAL");
        result.setManagerName("Vue Globale");
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }

    /**
     * Obtenir la liste des managers avec des plannings actifs
     *
    public List<ManagerStatisticsDTO> getAllManagersWithStatistics() {
        int currentYear = LocalDate.now().getYear();

        List<Object[]> managersData = holidayPlanningRepository
                .findManagersWithPlannings(currentYear);

        List<ManagerStatisticsDTO> result = new ArrayList<>();

        for (Object[] row : managersData) {
            Long managerId = (Long) row[0];
            String managerName = (String) row[1];

            // Obtenir les statistiques pour ce manager
            ManagerStatisticsDTO stats = getManagerStatistics(managerId.toString());
            result.add(stats);
        }

        return result;
    }
}*/

package com.holidaymatrix.service;

import com.holidaymatrix.dto.ManagerStatisticsDTO;
import com.holidaymatrix.dto.WeeklyStatisticsDTO;
import com.holidaymatrix.model.Employee;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.HolidayPlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPlanningRepository holidayPlanningRepository;

    /**
     * Récupère tous les noms des managers directs
     */
    public List<String> getAllManagerNames() {
        return employeeRepository.findAllManagers();
    }

    /**
     * Récupère les statistiques pour un manager spécifique
     */
    public ManagerStatisticsDTO getManagerStatistics(String managerName) {
        // Trouver le manager par son nom
        Employee manager = employeeRepository.findManagerByName(managerName);
        if (manager == null) {
            throw new RuntimeException("Manager not found: " + managerName);
        }

        // Utiliser l'année courante
        int currentYear = LocalDate.now().getYear();

        // Utiliser la méthode du repository pour récupérer les statistiques par semaine
        List<Object[]> weeklyData = holidayPlanningRepository
                .countPlanningsByWeekForManager(manager.getId(), currentYear);

        // Créer une map pour faciliter la manipulation des données
        Map<Integer, Long> holidaysByWeek = new HashMap<>();
        for (Object[] row : weeklyData) {
            Integer weekNumber = (Integer) row[0];
            Long count = (Long) row[1];
            holidaysByWeek.put(weekNumber, count);
        }

        // Convertir en liste de WeeklyStatisticsDTO (52 semaines dans l'année)
        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        // Créer le DTO final
        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId(manager.getId().toString());
        result.setManagerName(manager.getName());
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }

    /**
     * Version alternative avec l'ID du manager
     */
    public ManagerStatisticsDTO getManagerStatisticsById(Long managerId) {
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + managerId));

        int currentYear = LocalDate.now().getYear();

        List<Object[]> weeklyData = holidayPlanningRepository
                .countPlanningsByWeekForManager(managerId, currentYear);

        Map<Integer, Long> holidaysByWeek = new HashMap<>();
        for (Object[] row : weeklyData) {
            Integer weekNumber = (Integer) row[0];
            Long count = (Long) row[1];
            holidaysByWeek.put(weekNumber, count);
        }

        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId(manager.getId().toString());
        result.setManagerName(manager.getName());
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }

    /**
     * Méthode pour obtenir les statistiques globales (pour HOS et DG)
     */
    public ManagerStatisticsDTO getGlobalStatistics() {
        int currentYear = LocalDate.now().getYear();

        List<Object[]> weeklyData = holidayPlanningRepository
                .countPlanningsByWeekGlobal(currentYear);

        Map<Integer, Long> holidaysByWeek = new HashMap<>();
        for (Object[] row : weeklyData) {
            Integer weekNumber = (Integer) row[0];
            Long count = (Long) row[1];
            holidaysByWeek.put(weekNumber, count);
        }

        List<WeeklyStatisticsDTO> weeklyStats = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            WeeklyStatisticsDTO weekStat = new WeeklyStatisticsDTO();
            weekStat.setWeekNumber(week);
            weekStat.setPlannedHolidays(
                    holidaysByWeek.getOrDefault(week, 0L).intValue()
            );
            weeklyStats.add(weekStat);
        }

        ManagerStatisticsDTO result = new ManagerStatisticsDTO();
        result.setManagerId("GLOBAL");
        result.setManagerName("Vue Globale");
        result.setWeeklyStatistics(weeklyStats);

        return result;
    }

    /**
     * Obtenir la liste des managers avec des plannings actifs
     */
    public List<ManagerStatisticsDTO> getAllManagersWithStatistics() {
        int currentYear = LocalDate.now().getYear();

        List<Object[]> managersData = holidayPlanningRepository
                .findManagersWithPlannings(currentYear);

        List<ManagerStatisticsDTO> result = new ArrayList<>();

        for (Object[] row : managersData) {
            Long managerId = (Long) row[0];
            String managerName = (String) row[1];

            // Obtenir les statistiques pour ce manager
            ManagerStatisticsDTO stats = getManagerStatisticsById(managerId);
            result.add(stats);
        }

        return result;
    }

    /**
     * Obtenir les employés d'un manager spécifique
     */
    public List<Employee> getEmployeesByManager(String managerName) {
        Employee manager = employeeRepository.findManagerByName(managerName);
        if (manager == null) {
            throw new RuntimeException("Manager not found: " + managerName);
        }

        return employeeRepository.findByManagerLong(manager.getId());
    }

    /**
     * Obtenir le nombre d'employés par manager
     */
    public Map<String, Integer> getEmployeeCountByManager() {
        List<Object[]> data = employeeRepository.countEmployeesByManager();
        Map<String, Integer> result = new HashMap<>();

        for (Object[] row : data) {
            String managerName = (String) row[1];
            Long count = (Long) row[2];
            result.put(managerName, count.intValue());
        }

        return result;
    }
}