package com.holidaymatrix.service;

import com.holidaymatrix.dto.MatrixDTO;
import com.holidaymatrix.dto.MatrixEntryDTO;
import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.MatrixEntry;
import com.holidaymatrix.model.Position;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.AnnualMatrixRepository;
import com.holidaymatrix.repository.MatrixEntryRepository;
import com.holidaymatrix.repository.PositionRepository;
import com.holidaymatrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MatrixService {

    @Autowired
    private AnnualMatrixRepository matrixRepository;

    @Autowired
    private MatrixEntryRepository entryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<MatrixDTO> getAllMatrices() {
        return matrixRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /*public List<MatrixDTO> getAllMatrices() {
        List<AnnualMatrix> matrices = matrixRepository.findAll();
        return matrices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }*/

    /*public List<MatrixDTO> getAllMatrices() {
        List<AnnualMatrix> matrices = matrixRepository.findAll();
        return matrices.stream()
                .map(matrix -> convertToDTO(matrix))  // Utilisation d'une lambda explicite
                .collect(Collectors.toList());
    }*/

    /*public List<MatrixDTO> getAllMatrices() {
        List<AnnualMatrix> matrices = matrixRepository.findAll();
        List<MatrixDTO> dtoList = new ArrayList<>();

        for (AnnualMatrix matrix : matrices) {
            dtoList.add(convertToDTO(matrix));
        }

        return dtoList;
    }*/

    public Optional<MatrixDTO> getMatrixById(Long id) {
        return matrixRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<MatrixDTO> getMatrixByYear(Integer year) {
        return matrixRepository.findByYear(year)
                .map(this::convertToDTO);
    }

    @Transactional
    public Optional<MatrixDTO> createMatrix(MatrixDTO dto, Long createdById) {
        return userRepository.findById(createdById)
                .map(user -> {
                    AnnualMatrix matrix = new AnnualMatrix();
                    matrix.setYear(dto.getYear());
                    matrix.setCreatedBy(user);
                    matrix.setCreatedAt(LocalDateTime.now());
                    matrix.setHosValidated(false);
                    matrix.setDgValidated(false);
                    matrix.setEntries(new ArrayList<>());

                    AnnualMatrix savedMatrix = matrixRepository.save(matrix);

                    // Création des entrées si fournies
                    if (dto.getEntries() != null && !dto.getEntries().isEmpty()) {
                        for (MatrixEntryDTO entryDTO : dto.getEntries()) {
                            positionRepository.findById(entryDTO.getPositionId())
                                    .ifPresent(position -> {
                                        MatrixEntry entry = new MatrixEntry();
                                        entry.setMatrix(savedMatrix);
                                        entry.setPosition(position);
                                        entry.setHeadcount(entryDTO.getHeadcount());
                                        entry.setPlannedHolidayCritical(entryDTO.getPlannedHolidayCritical());
                                        entry.setPlannedHolidayMedium(entryDTO.getPlannedHolidayMedium());
                                        entry.setPlannedHolidayLow(entryDTO.getPlannedHolidayLow());
                                        entry.setNonPlannedHolidayCritical(entryDTO.getNonPlannedHolidayCritical());
                                        entry.setNonPlannedHolidayMedium(entryDTO.getNonPlannedHolidayMedium());
                                        entry.setNonPlannedHolidayLow(entryDTO.getNonPlannedHolidayLow());

                                        entryRepository.save(entry);
                                        savedMatrix.getEntries().add(entry);
                                    });
                        }
                    }

                    return convertToDTO(savedMatrix);
                });
    }

    @Transactional
    public Optional<MatrixDTO> validateMatrixByHOS(Long matrixId, Long hosId) {
        return userRepository.findById(hosId)
                .flatMap(hos -> matrixRepository.findById(matrixId)
                        .map(matrix -> {
                            matrix.setHosValidated(true);
                            matrix.setHosValidatedBy(hos);
                            matrix.setHosValidatedAt(LocalDateTime.now());

                            AnnualMatrix validatedMatrix = matrixRepository.save(matrix);
                            return convertToDTO(validatedMatrix);
                        }));
    }

    @Transactional
    public Optional<MatrixDTO> validateMatrixByDG(Long matrixId, Long dgId) {
        return userRepository.findById(dgId)
                .flatMap(dg -> matrixRepository.findById(matrixId)
                        .map(matrix -> {
                            matrix.setDgValidated(true);
                            matrix.setDgValidatedBy(dg);
                            matrix.setDgValidatedAt(LocalDateTime.now());

                            AnnualMatrix validatedMatrix = matrixRepository.save(matrix);
                            return convertToDTO(validatedMatrix);
                        }));
    }

    @Transactional
    public Optional<MatrixEntryDTO> addMatrixEntry(Long matrixId, MatrixEntryDTO dto) {
        return matrixRepository.findById(matrixId)
                .flatMap(matrix -> positionRepository.findById(dto.getPositionId())
                        .map(position -> {
                            // Vérifier si une entrée existe déjà pour cette position
                            Optional<MatrixEntry> existingEntry = entryRepository.findByMatrixAndPosition(matrix, position);

                            MatrixEntry entry;
                            if (existingEntry.isPresent()) {
                                entry = existingEntry.get();
                                entry.setHeadcount(dto.getHeadcount());
                                entry.setPlannedHolidayCritical(dto.getPlannedHolidayCritical());
                                entry.setPlannedHolidayMedium(dto.getPlannedHolidayMedium());
                                entry.setPlannedHolidayLow(dto.getPlannedHolidayLow());
                                entry.setNonPlannedHolidayCritical(dto.getNonPlannedHolidayCritical());
                                entry.setNonPlannedHolidayMedium(dto.getNonPlannedHolidayMedium());
                                entry.setNonPlannedHolidayLow(dto.getNonPlannedHolidayLow());
                            } else {
                                entry = new MatrixEntry();
                                entry.setMatrix(matrix);
                                entry.setPosition(position);
                                entry.setHeadcount(dto.getHeadcount());
                                entry.setPlannedHolidayCritical(dto.getPlannedHolidayCritical());
                                entry.setPlannedHolidayMedium(dto.getPlannedHolidayMedium());
                                entry.setPlannedHolidayLow(dto.getPlannedHolidayLow());
                                entry.setNonPlannedHolidayCritical(dto.getNonPlannedHolidayCritical());
                                entry.setNonPlannedHolidayMedium(dto.getNonPlannedHolidayMedium());
                                entry.setNonPlannedHolidayLow(dto.getNonPlannedHolidayLow());

                                matrix.getEntries().add(entry);
                            }

                            MatrixEntry savedEntry = entryRepository.save(entry);
                            return convertEntryToDTO(savedEntry);
                        }));
    }

    public boolean deleteMatrix(Long id) {
        if (matrixRepository.existsById(id)) {
            matrixRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MatrixDTO convertToDTO(AnnualMatrix matrix) {
        MatrixDTO dto = new MatrixDTO();
        dto.setId(matrix.getId());
        dto.setYear(matrix.getYear());

        if (matrix.getCreatedBy() != null) {
            dto.setCreatedById(matrix.getCreatedBy().getId());
            dto.setCreatedByUsername(matrix.getCreatedBy().getUsername());
        }

        dto.setCreatedAt(matrix.getCreatedAt());
        dto.setHosValidated(matrix.isHosValidated());
        dto.setHosValidatedAt(matrix.getHosValidatedAt());

        if (matrix.getHosValidatedBy() != null) {
            dto.setHosValidatedById(matrix.getHosValidatedBy().getId());
            dto.setHosValidatedByUsername(matrix.getHosValidatedBy().getUsername());
        }

        dto.setDgValidated(matrix.isDgValidated());
        dto.setDgValidatedAt(matrix.getDgValidatedAt());

        if (matrix.getDgValidatedBy() != null) {
            dto.setDgValidatedById(matrix.getDgValidatedBy().getId());
            dto.setDgValidatedByUsername(matrix.getDgValidatedBy().getUsername());
        }

        // Conversion des entrées
        if (matrix.getEntries() != null) {
            dto.setEntries(matrix.getEntries().stream()
                    .map(this::convertEntryToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private MatrixEntryDTO convertEntryToDTO(MatrixEntry entry) {
        MatrixEntryDTO dto = new MatrixEntryDTO();
        dto.setId(entry.getId());
        dto.setMatrixId(entry.getMatrix().getId());
        dto.setPositionId(entry.getPosition().getId());
        dto.setPositionTitle(entry.getPosition().getTitle());
        dto.setHeadcount(entry.getHeadcount());
        dto.setPlannedHolidayCritical(entry.getPlannedHolidayCritical());
        dto.setPlannedHolidayMedium(entry.getPlannedHolidayMedium());
        dto.setPlannedHolidayLow(entry.getPlannedHolidayLow());
        dto.setNonPlannedHolidayCritical(entry.getNonPlannedHolidayCritical());
        dto.setNonPlannedHolidayMedium(entry.getNonPlannedHolidayMedium());
        dto.setNonPlannedHolidayLow(entry.getNonPlannedHolidayLow());
        return dto;
    }
}