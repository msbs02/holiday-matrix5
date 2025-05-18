/*package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnualMatrixRepository extends JpaRepository<AnnualMatrix, Long> {
    Optional<AnnualMatrix> findByYear(Integer year);
    List<AnnualMatrix> findByCreatedBy(User user);
    List<AnnualMatrix> findByHosValidated(boolean validated);
    List<AnnualMatrix> findByDgValidated(boolean validated);
}*/

//make en commentaire le 10/05/2025  a 22:38

package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnualMatrixRepository extends JpaRepository<AnnualMatrix, Long> {
    List<AnnualMatrix> findByManager(User manager);

    Optional<AnnualMatrix> findByYear(Integer year);

    List<AnnualMatrix> findByManagerAndYear(User manager, int year);
}
/*

package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnualMatrixRepository extends JpaRepository<AnnualMatrix, Long> {
    Optional<AnnualMatrix> findByYear(Integer year);

}*/