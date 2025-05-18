package com.holidaymatrix.service;

import com.holidaymatrix.dto.OrganizationDTO;
import com.holidaymatrix.model.Organization;
import com.holidaymatrix.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<OrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrganizationDTO> getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public OrganizationDTO createOrganization(OrganizationDTO dto) {
        Organization organization = new Organization();
        organization.setName(dto.getName());

        Organization savedOrganization = organizationRepository.save(organization);
        return convertToDTO(savedOrganization);
    }

    public Optional<OrganizationDTO> updateOrganization(Long id, OrganizationDTO dto) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    organization.setName(dto.getName());
                    return convertToDTO(organizationRepository.save(organization));
                });
    }

    public boolean deleteOrganization(Long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        return dto;
    }
}