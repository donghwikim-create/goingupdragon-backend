package com.goingupdragon.going_up_dragon.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.like.CourseLikeDTO;
import com.goingupdragon.going_up_dragon.enums.Enums;
import com.goingupdragon.going_up_dragon.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) { this.courseService = courseService;}

    @Operation(
            summary = "Get course details",
            description = "Retrieves detailed information about a course using the provided course ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved course details"),
            @ApiResponse(responseCode = "400", description = "Invalid course ID provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Integer courseId) {
        CourseDTO course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @Operation(
            summary = "Get all courses by instructor ID",
            description = "Retrieves a list of courses taught by the specified instructor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the instructor's course list"),
            @ApiResponse(responseCode = "400", description = "Invalid instructor ID provided"),
            @ApiResponse(responseCode = "404", description = "Instructor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable Integer instructorId) {
        List<CourseDTO> courseList = courseService.getCourseList(instructorId);
        return ResponseEntity.ok((courseList));
    }

    @Operation(
            summary = "Get top-rated courses",
            description = "Retrieves a list of courses with an average rating of 3.5 or higher, selected randomly."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved top-rated courses"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/top-rated")
    public ResponseEntity<List<CourseDTO>> getTopRatedCourses(@RequestParam(defaultValue = "-1") int infoId,
                                                              @RequestParam(defaultValue = "10") int limit) {
        List<CourseDTO> courses = courseService.getTopRatedCourses(infoId, limit);
        return ResponseEntity.ok(courses);
    }

    @Operation(
            summary = "Get free courses excluding enrolled",
            description = "Retrieves a list of free courses that the user has not enrolled in, selected randomly."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved free courses"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/free")
    public ResponseEntity<List<CourseDTO>> getFreeCourses(
            @RequestParam(defaultValue = "-1") int infoId,
            @RequestParam(defaultValue = "10") int limit) {

        List<CourseDTO> courses = courseService.getFreeCoursesNotEnrolled(infoId, limit);
        return ResponseEntity.ok(courses);
    }

    @Operation(
            summary = "Get Easy courses excluding enrolled",
            description = "Retrieves a list of Easy courses that the user has not enrolled in, selected randomly."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Easy courses"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/level")
    public ResponseEntity<List<CourseDTO>> getEasyCourses(
            @RequestParam(defaultValue = "입문") String courseLevel,
            @RequestParam(defaultValue = "-1") int infoId,
            @RequestParam(defaultValue = "10") int limit) {

        Enums.CourseLevel level = Enums.CourseLevel.valueOf(courseLevel);
        List<CourseDTO> courses = courseService.getEasyCoursesNotEnrolled(level, infoId, limit);
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/filtered")
    public ResponseEntity<Page<CourseDTO>> getCoursesByFiltersAndSort(
            @RequestParam(defaultValue = "0") Integer mainCategory,
            @RequestParam(defaultValue = "0") Integer subCategory,
            @RequestParam(defaultValue = "모두") Enums.CourseLevel level,
            @RequestParam(defaultValue = "모두") Enums.CourseLanguage language,
            @RequestParam(required = false) String timeFilter,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) List<Integer> selectedTags
    ) {

        // ✅ selectedTags가 null이면 빈 리스트로 변환
        List<Integer> safeSelectedTags = Optional.ofNullable(selectedTags)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull) // ✅ 리스트 내부의 null 값 제거
                .toList();

        Page<CourseDTO> courses = courseService.getCoursesByFiltersAndSort(
                mainCategory, subCategory, level, language, timeFilter, selectedTags, sortBy, size, offset
        );
        return ResponseEntity.ok(courses);
    }
}
