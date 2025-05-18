package com.holidaymatrix.repository;

import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.Position;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
    List<Employee> findByDirectManager(Employee manager);
    List<Employee> findByPosition(Position position);
    Optional<Employee> findByEmployeeId(String employeeId);
}