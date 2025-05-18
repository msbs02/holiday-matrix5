package com.holidaymatrix.service;

import com.holidaymatrix.dto.CommentRequest;
import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.Comment;
import com.holidaymatrix.model.Holiday;
import com.holidaymatrix.model.User;
import com.holidaymatrix.repository.AnnualMatrixRepository;
import com.holidaymatrix.repository.CommentRepository;
import com.holidaymatrix.repository.HolidayRepository;
import com.holidaymatrix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnnualMatrixRepository matrixRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Transactional
    public Comment addComment(String username, CommentRequest request) {
        logger.info("Adding comment by user: {}", username);

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setAuthor(author);

        if (request.getMatrixId() != null) {
            AnnualMatrix matrix = matrixRepository.findById(request.getMatrixId())
                    .orElseThrow(() -> new RuntimeException("Matrix not found: " + request.getMatrixId()));
            comment.setMatrix(matrix);
        }

        if (request.getHolidayId() != null) {
            Holiday holiday = holidayRepository.findById(request.getHolidayId())
                    .orElseThrow(() -> new RuntimeException("Holiday not found: " + request.getHolidayId()));
            comment.setHoliday(holiday);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getMatrixComments(Long matrixId) {
        AnnualMatrix matrix = matrixRepository.findById(matrixId)
                .orElseThrow(() -> new RuntimeException("Matrix not found: " + matrixId));

        return commentRepository.findByMatrixOrderByCreatedAtDesc(matrix);
    }

    public List<Comment> getHolidayComments(Long holidayId) {
        Holiday holiday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found: " + holidayId));

        return commentRepository.findByHolidayOrderByCreatedAtDesc(holiday);
    }
}