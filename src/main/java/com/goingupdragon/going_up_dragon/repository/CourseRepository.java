package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId")
    Course findCourse (@Param("courseId") Integer courseId);

    //@Query ("SELECT c FROM Course c WHERE c.instructor.infoId = :infoId")
    //List<Course> findInstructorCourse (Integer infoId);
}
