package com.holidaymatrix.repository;

import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.Position;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
    List<Employee> findByDirectManager(Employee manager);
    List<Employee> findByPosition(Position position);
    Optional<Employee> findByEmployeeId(String employeeId);
    //@Query("SELECT DISTINCT e.manager.name FROM Employee e WHERE e.manager IS NOT NULL")
    //List<String> findAllManagers();

    @Query("SELECT e FROM Employee e WHERE e.directManager.id = :managerId")
    List<Employee> findByManager(@Param("managerId") String managerId);


    /**
     * Trouve tous les noms des managers distincts
     */
    @Query("SELECT DISTINCT e.directManager.name FROM Employee e WHERE e.directManager IS NOT NULL")
    List<String> findAllManagers();

    /**
     * Trouve tous les employés d'un manager spécifique en utilisant l'ID String
     */
    //@Query("SELECT e FROM Employee e WHERE e.manager.id = :managerId")
    //List<Employee> findByManager(@Param("managerId") String managerId);

    /**
     * Trouve tous les employés d'un manager spécifique en utilisant l'ID Long
     */
    @Query("SELECT e FROM Employee e WHERE e.directManager.id = :managerId")
    List<Employee> findByManagerLong(@Param("managerId") Long managerId);

    /**
     * Trouve un manager par son nom
     */
    @Query("SELECT e FROM Employee e WHERE e.name = :managerName AND e.id IN " +
            "(SELECT DISTINCT emp.directManager.id FROM Employee emp WHERE emp.directManager IS NOT NULL)")
    Employee findManagerByName(@Param("managerName") String managerName);

    /**
     * Trouve tous les managers avec leurs informations complètes
     */
    @Query("SELECT DISTINCT e.directManager FROM Employee e WHERE e.directManager IS NOT NULL")
    List<Employee> findAllManagerObjects();

    /**
     * Compte le nombre d'employés par manager
     */
    @Query("SELECT e.directManager.id, e.directManager.name, COUNT(e) " +
            "FROM Employee e " +
            "WHERE e.directManager IS NOT NULL " +
            "GROUP BY e.directManager.id, e.directManager.name")
    List<Object[]> countEmployeesByManager();

}