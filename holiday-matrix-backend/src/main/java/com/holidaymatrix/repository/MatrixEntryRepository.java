package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.MatrixEntry;
import com.holidaymatrix.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatrixEntryRepository extends JpaRepository<MatrixEntry, Long> {
    List<MatrixEntry> findByMatrix(AnnualMatrix matrix);
    Optional<MatrixEntry> findByMatrixAndPosition(AnnualMatrix matrix, Position position);
}
/*
package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.MatrixEntry;
import com.holidaymatrix.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatrixEntryRepository extends JpaRepository<MatrixEntry, Long> {
    Optional<MatrixEntry> findByMatrixAndPosition(AnnualMatrix matrix, Position position);
}*/