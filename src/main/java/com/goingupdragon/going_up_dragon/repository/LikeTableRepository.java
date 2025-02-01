package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.LikeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeTableRepository extends JpaRepository<LikeTable, Integer> {

    // ✅ 특정 강의(courseId)에 대한 좋아요 목록 조회
    List<LikeTable> findByCourseId(Integer courseId);

    // ✅ 특정 리뷰(reviewId)에 대한 좋아요 목록 조회
    List<LikeTable> findByReviewId(Integer reviewId);

    // ✅ 특정 QnA(qnaId)에 대한 좋아요 목록 조회
    List<LikeTable> findByQnaId(Integer qnaId);

    // ✅ 특정 유저가 좋아요한 QnA 목록 조회
    List<LikeTable> findByUserIdAndQnaIdIsNotNull(Integer userId);

    // ✅ 특정 유저가 좋아요한 Course 목록 조회
    List<LikeTable> findByUserIdAndCourseIdIsNotNull(Integer userId);

    // ✅ 특정 유저가 좋아요한 Review 목록 조회
    List<LikeTable> findByUserIdAndReviewIdIsNotNull(Integer userId);

    // ✅ 특정 유저가 특정 QnA에 좋아요를 눌렀는지 확인
    boolean existsByUserIdAndQnaId(Integer userId, Integer qnaId);

    // ✅ 특정 유저가 특정 Course에 좋아요를 눌렀는지 확인
    boolean existsByUserIdAndCourseId(Integer userId, Integer courseId);

    // ✅ 특정 유저가 특정 Review에 좋아요를 눌렀는지 확인
    boolean existsByUserIdAndReviewId(Integer userId, Integer reviewId);

    // ✅ 특정 유저의 좋아요 데이터 삭제 (QnA, Course, Review 개별 삭제)
    void deleteByUserIdAndQnaId(Integer userId, Integer qnaId);
    void deleteByUserIdAndCourseId(Integer userId, Integer courseId);
    void deleteByUserIdAndReviewId(Integer userId, Integer reviewId);
}
