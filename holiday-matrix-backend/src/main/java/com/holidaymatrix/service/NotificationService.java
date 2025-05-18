/*package com.holidaymatrix.service;

import com.holidaymatrix.dto.NotificationDTO;
import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.NotificationRepository;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getNotificationsByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.findByUser(user).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<NotificationDTO> getUnreadNotificationsByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.findByUserAndIsRead(user, false).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public Optional<NotificationDTO> createNotification(Long userId, String message) {
        return userRepository.findById(userId)
                .map(user -> {
                    Notification notification = new Notification();
                    notification.setUser(user);
                    notification.setMessage(message);
                    notification.setRead(false);
                    notification.setCreatedAt(LocalDateTime.now());

                    Notification savedNotification = notificationRepository.save(notification);
                    return convertToDTO(savedNotification);
                });
    }

    public Optional<NotificationDTO> markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> {
                    notification.setRead(true);

                    Notification updatedNotification = notificationRepository.save(notification);
                    return convertToDTO(updatedNotification);
                });
    }

    public boolean deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());

        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
            dto.setUsername(notification.getUser().getUsername());
        }

        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}*/

package com.holidaymatrix.service;

import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.NotificationRepository;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Notification createNotification(User user, String message, Notification.NotificationType type) {
        logger.info("Creating notification for user: {}", user.getUsername());

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);

        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Notification> getUnreadNotifications(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return notificationRepository.findByUserAndReadIsFalseOrderByCreatedAtDesc(user);
    }

    public long getUnreadCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return notificationRepository.countByUserAndReadIsFalse(user);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<Notification> unreadNotifications = notificationRepository
                .findByUserAndReadIsFalseOrderByCreatedAtDesc(user);

        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}