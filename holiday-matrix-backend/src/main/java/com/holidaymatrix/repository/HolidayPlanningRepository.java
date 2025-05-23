/*
package com.holidaymatrix.repository;

import com.holidaymatrix.model.Employee;
import com.holidaymatrix.model.HolidayPeriod;
import com.holidaymatrix.model.HolidayPlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    List<HolidayPlanning> findByEmployeeIdIn(List<Long> employeeIds);


    /**
     * Trouve tous les plannings de congés pour une liste d'employés
     * Utilisé pour récupérer les données des employés d'un manager spécifique

    //List<HolidayPlanning> findByEmployeeIdIn(List<Long> employeeIds);

//import org.springframework.data.jpa.repository.Query;


     * Trouve tous les plannings de congés pour un employé spécifique
     *
    List<HolidayPlanning> findByEmployeeId(Long employeeId);

    /**
     * Trouve tous les plannings de congés pour un manager spécifique
     * (via la relation employee -> manager)
     *
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId")
    List<HolidayPlanning> findByManagerId(@Param("managerId") Long managerId);

    /**
     * Compte le nombre de congés planifiés par semaine pour un manager
     * Retourne les données pour créer les statistiques
     *
    @Query("SELECT WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate) " +
            "ORDER BY WEEK(hp.startDate)")
    List<Object[]> countPlanningsByWeekForManager(@Param("managerId") Long managerId,
                                                  @Param("year") Integer year);

    /**
     * Trouve tous les plannings de congés pour une année spécifique
     * Utilisé par HOS et DG pour avoir une vue globale
     *
    @Query("SELECT hp FROM HolidayPlanning hp WHERE YEAR(hp.startDate) = :year")
    List<HolidayPlanning> findByYear(@Param("year") Integer year);

    /**
     * Compte le nombre total de congés planifiés par semaine pour tous les managers
     * Vue globale pour HOS et DG
     *
    @Query("SELECT WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate) " +
            "ORDER BY WEEK(hp.startDate)")
    List<Object[]> countPlanningsByWeekGlobal(@Param("year") Integer year);

    /**
     * Trouve les plannings par status (pour filtrer selon les validations)
     *
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND hp.status = :status")
    List<HolidayPlanning> findByManagerIdAndStatus(@Param("managerId") Long managerId,
                                                   @Param("status") String status);

    /**
     * Compte les employés en congé par type de criticité et par semaine
     * Pour les statistiques avancées selon les couleurs (rouge/jaune/vert)
     *
    @Query("SELECT WEEK(hp.startDate) as weekNumber, hp.criticalityLevel as criticality, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate), hp.criticalityLevel " +
            "ORDER BY WEEK(hp.startDate), hp.criticalityLevel")
    List<Object[]> countPlanningsByWeekAndCriticalityForManager(@Param("managerId") Long managerId,
                                                                @Param("year") Integer year);

    /**
     * Vue globale des statistiques par criticité pour HOS et DG
     *
    @Query("SELECT WEEK(hp.startDate) as weekNumber, hp.criticalityLevel as criticality, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate), hp.criticalityLevel " +
            "ORDER BY WEEK(hp.startDate), hp.criticalityLevel")
    List<Object[]> countPlanningsByWeekAndCriticalityGlobal(@Param("year") Integer year);

    /**
     * Trouve les plannings pour une période spécifique
     *
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "WHERE hp.startDate >= :startDate " +
            "AND hp.endDate <= :endDate")
    List<HolidayPlanning> findByDateRange(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    /**
     * Statistiques par organisation pour la direction générale
     *
    @Query("SELECT e.organization.name as orgName, WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY e.organization.name, WEEK(hp.startDate) " +
            "ORDER BY e.organization.name, WEEK(hp.startDate)")
    List<Object[]> countPlanningsByOrganizationAndWeek(@Param("year") Integer year);

    /**
     * Trouve tous les managers qui ont des employés avec des plannings
     *
    @Query("SELECT DISTINCT e.manager.id, e.manager.name " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.manager IS NOT NULL " +
            "AND YEAR(hp.startDate) = :year")
    List<Object[]> findManagersWithPlannings(@Param("year") Integer year);

    /**
     * Compte le nombre total d'employés par manager ayant des plannings
     *
    @Query("SELECT e.manager.id as managerId, e.manager.name as managerName, COUNT(DISTINCT e.id) as employeeCount " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.manager IS NOT NULL " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY e.manager.id, e.manager.name")
    List<Object[]> countEmployeesByManager(@Param("year") Integer year);

}*/

package com.holidaymatrix.repository;

import com.holidaymatrix.model.HolidayPlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayPlanningRepository extends JpaRepository<HolidayPlanning, Long> {

    /**
     * Trouve tous les plannings de congés pour une liste d'employés
     */
    List<HolidayPlanning> findByEmployeeIdIn(List<Long> employeeIds);

    /**
     * Trouve tous les plannings de congés pour un employé spécifique
     */
    List<HolidayPlanning> findByEmployeeId(Long employeeId);

    /**
     * Trouve tous les plannings de congés pour un manager direct spécifique
     */
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId")
    List<HolidayPlanning> findByManagerId(@Param("managerId") Long managerId);

    /**
     * Compte le nombre de congés planifiés par semaine pour un manager direct
     */
    @Query("SELECT WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate) " +
            "ORDER BY WEEK(hp.startDate)")
    List<Object[]> countPlanningsByWeekForManager(@Param("managerId") Long managerId,
                                                  @Param("year") Integer year);

    /**
     * Compte le nombre de congés planifiés par semaine pour une liste d'employés
     */
    @Query("SELECT WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "WHERE hp.employee.id IN :employeeIds " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate) " +
            "ORDER BY WEEK(hp.startDate)")
    List<Object[]> countPlanningsByWeekForEmployees(@Param("employeeIds") List<Long> employeeIds,
                                                    @Param("year") Integer year);

    /**
     * Trouve tous les plannings de congés pour une année spécifique
     */
    @Query("SELECT hp FROM HolidayPlanning hp WHERE YEAR(hp.startDate) = :year")
    List<HolidayPlanning> findByYear(@Param("year") Integer year);

    /**
     * Compte le nombre total de congés planifiés par semaine pour tous les employés
     * Vue globale pour HOS et DG
     */
    @Query("SELECT WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate) " +
            "ORDER BY WEEK(hp.startDate)")
    List<Object[]> countPlanningsByWeekGlobal(@Param("year") Integer year);

    /**
     * Trouve les plannings par status pour un manager
     */
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND hp.status = :status")
    List<HolidayPlanning> findByManagerIdAndStatus(@Param("managerId") Long managerId,
                                                   @Param("status") String status);

    /**
     * Compte les employés en congé par type de criticité et par semaine pour un manager
     */
    @Query("SELECT WEEK(hp.startDate) as weekNumber, hp.criticalityLevel as criticality, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager.id = :managerId " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate), hp.criticalityLevel " +
            "ORDER BY WEEK(hp.startDate), hp.criticalityLevel")
    List<Object[]> countPlanningsByWeekAndCriticalityForManager(@Param("managerId") Long managerId,
                                                                @Param("year") Integer year);

    /**
     * Vue globale des statistiques par criticité pour HOS et DG
     */
    @Query("SELECT WEEK(hp.startDate) as weekNumber, hp.criticalityLevel as criticality, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY WEEK(hp.startDate), hp.criticalityLevel " +
            "ORDER BY WEEK(hp.startDate), hp.criticalityLevel")
    List<Object[]> countPlanningsByWeekAndCriticalityGlobal(@Param("year") Integer year);

    /**
     * Trouve les plannings pour une période spécifique
     */
    @Query("SELECT hp FROM HolidayPlanning hp " +
            "WHERE hp.startDate >= :startDate " +
            "AND hp.endDate <= :endDate")
    List<HolidayPlanning> findByDateRange(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    /**
     * Statistiques par organisation pour la direction générale
     */
    @Query("SELECT e.position.organization.name as orgName, WEEK(hp.startDate) as weekNumber, COUNT(hp) as count " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE YEAR(hp.startDate) = :year " +
            "GROUP BY e.position.organization.name, WEEK(hp.startDate) " +
            "ORDER BY e.position.organization.name, WEEK(hp.startDate)")
    List<Object[]> countPlanningsByOrganizationAndWeek(@Param("year") Integer year);

    /**
     * Trouve tous les managers directs qui ont des employés avec des plannings
     */
    @Query("SELECT DISTINCT e.directManager.id, e.directManager.name " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager IS NOT NULL " +
            "AND YEAR(hp.startDate) = :year")
    List<Object[]> findManagersWithPlannings(@Param("year") Integer year);

    /**
     * Compte le nombre total d'employés par manager direct ayant des plannings
     */
    @Query("SELECT e.directManager.id as managerId, e.directManager.name as managerName, COUNT(DISTINCT e.id) as employeeCount " +
            "FROM HolidayPlanning hp " +
            "JOIN hp.employee e " +
            "WHERE e.directManager IS NOT NULL " +
            "AND YEAR(hp.startDate) = :year " +
            "GROUP BY e.directManager.id, e.directManager.name")
    List<Object[]> countEmployeesByManager(@Param("year") Integer year);
}