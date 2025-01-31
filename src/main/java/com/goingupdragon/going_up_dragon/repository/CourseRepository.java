package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
