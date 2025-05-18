package com.holidaymatrix.repository;

import com.holidaymatrix.model.HolidayPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayPeriodRepository extends JpaRepository<HolidayPeriod, Long> {
    List<HolidayPeriod> findByStartDateAfter(LocalDate date);
    List<HolidayPeriod> findByNotificationSent(boolean sent);
    List<HolidayPeriod> findByStartDateBetween(LocalDate start, LocalDate end);
}