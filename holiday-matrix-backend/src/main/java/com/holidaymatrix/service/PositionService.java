package com.holidaymatrix.service;

import com.holidaymatrix.dto.PositionDTO;
import com.holidaymatrix.model.Organization;
import com.holidaymatrix.model.Position;
import com.holidaymatrix.repository.OrganizationRepository;
import com.holidaymatrix.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PositionDTO> getPositionById(Long id) {
        return positionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<PositionDTO> getPositionsByOrganization(Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(organization -> positionRepository.findByOrganization(organization).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<PositionDTO> getCriticalPositions() {
        return positionRepository.findByIsCritical(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PositionDTO> createPosition(PositionDTO dto) {
        return organizationRepository.findById(dto.getOrganizationId())
                .map(organization -> {
                    Position position = new Position();
                    position.setTitle(dto.getTitle());
                    position.setDescription(dto.getDescription());
                    position.setOrganization(organization);
                    position.setCritical(dto.isCritical());

                    Position savedPosition = positionRepository.save(position);
                    return convertToDTO(savedPosition);
                });
    }

    public Optional<PositionDTO> updatePosition(Long id, PositionDTO dto) {
        return positionRepository.findById(id)
                .flatMap(position -> organizationRepository.findById(dto.getOrganizationId())
                        .map(organization -> {
                            position.setTitle(dto.getTitle());
                            position.setDescription(dto.getDescription());
                            position.setOrganization(organization);
                            position.setCritical(dto.isCritical());

                            Position updatedPosition = positionRepository.save(position);
                            return convertToDTO(updatedPosition);
                        }));
    }

    public boolean deletePosition(Long id) {
        if (positionRepository.existsById(id)) {
            positionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PositionDTO convertToDTO(Position position) {
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setTitle(position.getTitle());
        dto.setDescription(position.getDescription());
        dto.setOrganizationId(position.getOrganization().getId());
        dto.setOrganizationName(position.getOrganization().getName());
        dto.setCritical(position.isCritical());
        return dto;
    }
}