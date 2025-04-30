package com.holidaymatrix.controller;

import com.holidaymatrix.dto.EmployeeDashboardData;
import com.holidaymatrix.dto.ManagerDashboardData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    // Endpoint pour le dashboard employé
    @GetMapping("/employee")
    public EmployeeDashboardData getEmployeeDashboard() {
        EmployeeDashboardData data = new EmployeeDashboardData();
        data.setCompanyName("LEONI");

        // Configuration du calendrier
        EmployeeDashboardData.CalendarData calendar = new EmployeeDashboardData.CalendarData();
        calendar.setMonth("Février");
        calendar.setYear(2025);
        calendar.setHighlightedDate(26);
        calendar.setHolidayDates(Arrays.asList(14)); // par exemple, le 14 février est férié
        data.setCalendar(calendar);

        // Exemple de notifications
        EmployeeDashboardData.Notification notif = new EmployeeDashboardData.Notification();
        notif.setMessage("Nouvelle notification !");
        notif.setRead(false);
        data.setNotifications(Arrays.asList(notif));

        // Exemple de news
        EmployeeDashboardData.NewsItem news = new EmployeeDashboardData.NewsItem();
        news.setTitle("Mise à jour horaire");
        news.setContent("Travail possible le 4 octobre, repos le 14 février et du 30 octobre au 5 novembre 2023.");
        data.setNews(Arrays.asList(news));

        return data;
    }

    // Endpoint pour le dashboard manager
    @GetMapping("/manager")
    public ManagerDashboardData getManagerDashboard() {
        ManagerDashboardData data = new ManagerDashboardData();
        data.setManagerName("Jean Dupont");
        data.setWelcomeMessage("Bienvenue sur votre tableau de bord, " + data.getManagerName());

        // Exemple de demande de congé
        ManagerDashboardData.LeaveRequest req = new ManagerDashboardData.LeaveRequest();
        req.setTeamOrPosition("Equipe Sales");
        req.setLeaveCategory("Congé payé");
        req.setEmployeeName("Alice Martin");
        req.setStatus("En attente");

        data.setLeaveRequests(Arrays.asList(req));

        return data;
    }
}
