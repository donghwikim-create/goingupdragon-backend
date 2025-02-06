package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.enums.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId")
    Course findCourse (@Param("courseId") Integer courseId);

    @Query ("SELECT c FROM Course c WHERE c.instructor.infoId = :infoId")
    List<Course> findInstructorCourses (@Param("infoId")Integer infoId);

    @Query("SELECT c FROM Course c WHERE c.courseId IN :courseIds")
    List<Course> findCoursesByIds(@Param("courseIds") List<Integer> courseIds);

    @Query(value = """
    SELECT * FROM courses c
    WHERE price = 0
    AND NOT EXISTS (
        SELECT 1 FROM enrollments e WHERE e.course_id = c.course_id AND e.info_id = :infoId
    )
    ORDER BY RAND() 
    LIMIT :limit
    """, nativeQuery = true)
    List<Course> findFreeCoursesExcludingEnrolled(@Param("infoId") int infoId, @Param("limit") int limit);

    @Query(value = """
        SELECT * FROM courses 
    WHERE level = :level
    AND course_id NOT IN (
        SELECT e.course_id FROM enrollments e WHERE e.info_id = :infoId
    )
    ORDER BY RAND() 
    LIMIT :limit
    """, nativeQuery = true)
    List<Course> findCoursesByLevel(@Param("level") Enums.CourseLevel level, @Param("infoId") int infoId, @Param("limit") int limit);
}
