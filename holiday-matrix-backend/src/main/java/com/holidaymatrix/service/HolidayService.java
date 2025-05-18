package com.holidaymatrix.service;

import com.holidaymatrix.dto.HolidayRequest;
import com.holidaymatrix.model.Holiday;
import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.HolidayRepository;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class HolidayService {
    private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Holiday createHoliday(String username, HolidayRequest request) {
        logger.info("Creating holiday for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Holiday holiday = new Holiday();
        holiday.setName(request.getName());
        holiday.setStartDate(request.getStartDate());
        holiday.setEndDate(request.getEndDate());
        holiday.setUser(user);
        holiday.setPlanned(request.isPlanned());
        holiday.setCriticalityLevel(request.getCriticalityLevel());

        holiday = holidayRepository.save(holiday);

        // Notification au manager si l'utilisateur en a un
        if (user.getManager() != null) {
            notificationService.createNotification(
                    user.getManager(),
                    "Nouvelle demande de congé de " + user.getUsername() + " du "
                            + request.getStartDate() + " au " + request.getEndDate(),
                    Notification.NotificationType.HOLIDAY_REQUEST
            );
        }

        return holiday;
    }

    public List<Holiday> getUserHolidays(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return holidayRepository.findByUser(user);
    }

    public List<Holiday> getUpcomingUserHolidays(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return holidayRepository.findByUserAndStartDateGreaterThanEqual(user, LocalDate.now());
    }

    public List<Holiday> getTeamHolidays(String managerUsername) {
        User manager = userRepository.findByUsername(managerUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + managerUsername));

        return holidayRepository.findByManagerId(manager.getId());
    }

    @Transactional
    public Holiday updateHolidayStatus(Long holidayId, Holiday.HolidayStatus status) {
        Holiday holiday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found: " + holidayId));

        holiday.setStatus(status);
        holiday = holidayRepository.save(holiday);

        // Notifier l'employé du changement de statut
        notificationService.createNotification(
                holiday.getUser(),
                "Votre demande de congé du " + holiday.getStartDate() + " au " +
                        holiday.getEndDate() + " a été " + (status == Holiday.HolidayStatus.APPROVED ? "approuvée" : "rejetée"),
                Notification.NotificationType.PLANNING_CHANGE
        );

        return holiday;
    }

    public boolean checkHolidayConflict(LocalDate startDate, LocalDate endDate) {
        List<Holiday> overlappingHolidays = holidayRepository.findOverlappingHolidays(startDate, endDate);
        return !overlappingHolidays.isEmpty();
    }
}