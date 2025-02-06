package com.goingupdragon.going_up_dragon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goingupdragon.going_up_dragon.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

    List<Review>  findAllByCourse_CourseId(Integer courseId); // 강의 리뷰 전체 조회

    @Query("SELECT COUNT(r) FROM Review r WHERE r.course.courseId = :courseId")
    Integer findReviewCount(@Param("courseId") Integer courseId);

    @Query("SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.course.courseId = :courseId")
    Float findReviewRate(@Param("courseId") Integer courseId);
}
