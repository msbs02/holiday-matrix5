package com.holidaymatrix.repository;

import com.holidaymatrix.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Méthodes de recherche personnalisées si nécessaire
}