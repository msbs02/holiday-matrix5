package com.holidaymatrix.service;

import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.HolidayPeriod;
import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.EmployeeRepository;
import com.holidaymatrix.repository.HolidayPeriodRepository;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationSchedulerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HolidayPeriodRepository holidayPeriodRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Tâche planifiée qui s'exécute tous les jours à minuit pour vérifier
     * si des notifications doivent être envoyées pour les périodes de congés à venir.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void sendHolidayPeriodNotifications() {
        LocalDate twoWeeksFromNow = LocalDate.now().plusWeeks(2);

        // Recherche des périodes de congés qui débutent dans exactement deux semaines
        // et pour lesquelles aucune notification n'a encore été envoyée
        List<HolidayPeriod> upcomingPeriods = holidayPeriodRepository.findAll().stream()
                .filter(period -> period.getStartDate().equals(twoWeeksFromNow))
                .filter(period -> !period.isNotificationSent())
                .toList();

        for (HolidayPeriod period : upcomingPeriods) {
            // Envoyer des notifications aux managers
            List<Employee> managers = employeeRepository.findAll().stream()
                    .filter(employee -> employee.getUser() != null)
                    .filter(employee -> {
                        String role = employee.getUser().getRole();
                        return "MANAGER".equals(role) || "HEAD_OF_SERVICE".equals(role) || "DIRECTION_GENERALE".equals(role);
                    })
                    .toList();

            for (Employee manager : managers) {
                String message = "Rappel : La période de congés \"" + period.getName() + "\" débute dans deux semaines. " +
                        "Veuillez vérifier et valider les plannings de votre équipe.";

                //notificationService.createNotification(manager.getUser().getId(), message);
                // Dans NotificationSchedulerService.java
                // Obtenez l'utilisateur directement à partir de l'employé
                User user = manager.getUser();

                if (user != null) {
                    notificationService.createNotification(
                            user,
                            message,
                            Notification.NotificationType.VALIDATION_REMINDER
                    );
                }
            }

            // Marquer la période comme notifiée
            period.setNotificationSent(true);
            period.setNotificationDate(LocalDateTime.now());
            holidayPeriodRepository.save(period);
        }
    }

}