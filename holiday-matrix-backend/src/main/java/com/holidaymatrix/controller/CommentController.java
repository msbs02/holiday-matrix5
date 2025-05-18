package com.holidaymatrix.controller;

import com.holidaymatrix.dto.CommentRequest;
import com.holidaymatrix.model.Comment;
import com.holidaymatrix.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(
            Authentication authentication,
            @RequestBody CommentRequest request) {

        Comment comment = commentService.addComment(authentication.getName(), request);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/matrix/{matrixId}")
    public ResponseEntity<List<Comment>> getMatrixComments(@PathVariable Long matrixId) {
        List<Comment> comments = commentService.getMatrixComments(matrixId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/holiday/{holidayId}")
    public ResponseEntity<List<Comment>> getHolidayComments(@PathVariable Long holidayId) {
        List<Comment> comments = commentService.getHolidayComments(holidayId);
        return ResponseEntity.ok(comments);
    }
}