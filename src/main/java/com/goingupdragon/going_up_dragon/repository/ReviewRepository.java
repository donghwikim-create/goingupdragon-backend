package com.goingupdragon.going_up_dragon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goingupdragon.going_up_dragon.entity.Reviews;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Integer>{

    List<Reviews>  findAllByCourse_CourseId(Integer courseId); // 강의 리뷰 전체 조회
}
