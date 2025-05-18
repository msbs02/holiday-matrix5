package com.holidaymatrix.service;

import com.holidaymatrix.dto.AnnualMatrixRequest;
import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.Notification;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.AnnualMatrixRepository;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnnualMatrixService {
    private static final Logger logger = LoggerFactory.getLogger(AnnualMatrixService.class);

    @Autowired
    private AnnualMatrixRepository matrixRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public AnnualMatrix createMatrix(String username, AnnualMatrixRequest request) {
        logger.info("Creating matrix for manager: {}", username);

        User manager = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        AnnualMatrix matrix = new AnnualMatrix();
        matrix.setPosition(request.getPosition());
        matrix.setHc(request.getHc());
        matrix.setDescription(request.getDescription());
        matrix.setDirectManager(request.getDirectManager());
        matrix.setOrganization(request.getOrganization());
        matrix.setNextLevelManager(request.getNextLevelManager());
        matrix.setEmployeeCount(request.getEmployeeCount());
        matrix.setPlannedHolidayCriticality(request.getPlannedHolidayCriticality());
        matrix.setNonPlannedHolidayCriticality(request.getNonPlannedHolidayCriticality());
        matrix.setYear(request.getYear());
        matrix.setManager(manager);

        return matrixRepository.save(matrix);
    }

    public List<AnnualMatrix> getManagerMatrices(String username) {
        User manager = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return matrixRepository.findByManager(manager);
    }

    public List<AnnualMatrix> getYearMatrices(int year) {
        Optional<AnnualMatrix> matrixOptional = matrixRepository.findByYear(year);
        return matrixOptional.map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    @Transactional
    public AnnualMatrix updateHosValidation(Long matrixId, AnnualMatrix.ValidationStatus status) {
        AnnualMatrix matrix = matrixRepository.findById(matrixId)
                .orElseThrow(() -> new RuntimeException("Matrix not found: " + matrixId));

        matrix.setHosValidationStatus(status);
        matrix = matrixRepository.save(matrix);

        // Notifier le manager
        notificationService.createNotification(
                matrix.getManager(),
                "Votre matrice pour " + matrix.getPosition() + " a été " +
                        (status == AnnualMatrix.ValidationStatus.APPROVED ? "approuvée" : "rejetée") + " par le HOS",
                Notification.NotificationType.VALIDATION_REMINDER
        );

        return matrix;
    }

    @Transactional
    public AnnualMatrix updateDgValidation(Long matrixId, AnnualMatrix.ValidationStatus status) {
        AnnualMatrix matrix = matrixRepository.findById(matrixId)
                .orElseThrow(() -> new RuntimeException("Matrix not found: " + matrixId));

        matrix.setDgValidationStatus(status);
        matrix = matrixRepository.save(matrix);

        // Notifier le manager
        notificationService.createNotification(
                matrix.getManager(),
                "Votre matrice pour " + matrix.getPosition() + " a été " +
                        (status == AnnualMatrix.ValidationStatus.APPROVED ? "approuvée" : "rejetée") + " par la DG",
                Notification.NotificationType.VALIDATION_REMINDER
        );

        return matrix;
    }
}