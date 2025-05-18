package com.holidaymatrix.repository;

import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.HolidayPeriod;
import com.holidaymatrix.model.HolidayPlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayPlanningRepository extends JpaRepository<HolidayPlanning, Long> {
    List<HolidayPlanning> findByEmployee(Employee employee);
    List<HolidayPlanning> findByHolidayPeriod(HolidayPeriod period);
    List<HolidayPlanning> findByEmployeeAndHolidayPeriod(Employee employee, HolidayPeriod period);
    List<HolidayPlanning> findByStatus(String status);
    List<HolidayPlanning> findByManagerValidated(boolean validated);
    List<HolidayPlanning> findByHosValidated(boolean validated);
    List<HolidayPlanning> findByDgValidated(boolean validated);
}