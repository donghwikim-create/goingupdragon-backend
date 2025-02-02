package com.goingupdragon.going_up_dragon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goingupdragon.going_up_dragon.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

    List<Review>  findAllByCourse_CourseId(Integer courseId); // 강의 리뷰 전체 조회
}
