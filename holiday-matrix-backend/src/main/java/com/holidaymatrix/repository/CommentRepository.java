package com.holidaymatrix.repository;

import com.holidaymatrix.model.AnnualMatrix;
import com.holidaymatrix.model.Comment;
import com.holidaymatrix.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMatrixOrderByCreatedAtDesc(AnnualMatrix matrix);

    List<Comment> findByHolidayOrderByCreatedAtDesc(Holiday holiday);
}