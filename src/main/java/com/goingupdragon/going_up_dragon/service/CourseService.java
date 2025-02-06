package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.repository.CourseRepository;
import com.goingupdragon.going_up_dragon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    public CourseService(CourseRepository courseRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository; this.reviewRepository = reviewRepository;}

    public CourseDTO getCourse(Integer courseId) {
        Course course = courseRepository.findCourse(courseId);

        return convertToDTO(course);
    }

    public List<CourseDTO> getCourseList(Integer infoId) {
        List<Course> courseList =  courseRepository.findInstructorCourses(infoId);

        return courseList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    public CourseDTO convertToDTO(Course course){

        Float averageRating = reviewRepository.findReviewRate(course.getCourseId());
        Integer reviewCount = reviewRepository.findReviewCount(course.getCourseId());

        return new CourseDTO(course.getCourseId(),
                course.getCourseTitle(),
                course.getShortDescription(),
                course.getInstructor().getInfoId(),
                course.getInstructor().getNickname(),
                course.getMainCategory().getCategoryName(),
                course.getMainCategory().getCategoryId(),
                course.getSubCategory().getCategoryName(),
                course.getSubCategory().getCategoryId(),
                course.getLevel(),
                course.getLanguage(),
                course.getPrice(),
                course.getSubjectTagIds(),
                course.getSubjectTagNames(),
                course.getStartDate(),
                reviewCount != null ? reviewCount : 0, // ✅ `null` 방지
                averageRating = (averageRating != null)
                        ? (float) ((int) (averageRating * 10) / 10.0)  // 소수점 첫째 자리만 유지
                        : 0.0f
                );
    }
}
