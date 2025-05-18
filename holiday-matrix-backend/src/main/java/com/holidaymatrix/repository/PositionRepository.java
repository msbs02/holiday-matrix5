package com.holidaymatrix.repository;

import com.holidaymatrix.model.Organization;
import com.holidaymatrix.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByOrganization(Organization organization);
    List<Position> findByIsCritical(boolean isCritical);
}
/*
package com.holidaymatrix.repository;

import com.holidaymatrix.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}*/