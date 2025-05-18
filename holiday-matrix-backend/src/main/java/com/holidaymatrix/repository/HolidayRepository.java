package com.holidaymatrix.repository;

import com.holidaymatrix.model.Holiday;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByUser(User user);

    List<Holiday> findByUserAndStartDateGreaterThanEqual(User user, LocalDate date);

    @Query("SELECT h FROM Holiday h WHERE h.startDate <= ?2 AND h.endDate >= ?1")
    List<Holiday> findOverlappingHolidays(LocalDate start, LocalDate end);

    @Query("SELECT h FROM Holiday h JOIN h.user u WHERE u.manager.id = ?1")
    List<Holiday> findByManagerId(Long managerId);
}