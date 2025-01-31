package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "Get all reviews for a course",
            description = "Fetches all reviews associated with the specified course ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
            @ApiResponse(responseCode = "400", description = "Invalid course ID provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getReviews(@PathVariable Integer courseId) {
        if (courseId == null || courseId <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid course ID. Please provide a valid positive integer.");
        }

        try {
            return ResponseEntity.ok(reviewService.getReviewsByCourse(courseId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404 오류 반환
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching reviews: " + e.getMessage());
        }
    }
}