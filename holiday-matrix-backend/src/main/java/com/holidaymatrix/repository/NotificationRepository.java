/*package com.holidaymatrix.repository;

import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndIsRead(User user, boolean isRead);
}*/
package com.holidaymatrix.repository;

import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    List<Notification> findByUserAndReadIsFalseOrderByCreatedAtDesc(User user);

    long countByUserAndReadIsFalse(User user);
}