package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.LikeTable;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeTableRepository extends JpaRepository<LikeTable, Integer> {

    // ✅ 특정 강의(courseId)에 대한 좋아요 목록 조회
    List<LikeTable> findByCourse_CourseId(Integer courseId);

    // ✅ 특정 리뷰(reviewId)에 대한 좋아요 목록 조회
    List<LikeTable> findByReview_ReviewId(Integer reviewId);

    // ✅ 특정 QnA(qnaId)에 대한 좋아요 목록 조회
    List<LikeTable> findByQna_QnaId(Integer qnaId);


    // ✅ 특정 유저가 특정 Course에 좋아요를 눌렀는지 확인
    boolean existsByUser_InfoIdAndCourse_CourseId(Integer infoId, Integer courseId);

    // ✅ 특정 유저가 특정 QnA에 좋아요를 눌렀는지 확인
    boolean existsByUser_InfoIdAndQna_QnaId(Integer infoId, Integer qnaId);

    // ✅ 특정 유저가 특정 Review에 좋아요를 눌렀는지 확인
    boolean existsByUser_InfoIdAndReview_ReviewId(Integer infoId, Integer reviewId);

    // ✅ 특정 유저가 좋아요한 QnA 목록 조회
    List<LikeTable> findByUser_InfoIdAndQna_QnaIdIsNotNull(Integer infoId);

    // ✅ 특정 유저가 좋아요한 Course 목록 조회
    List<LikeTable> findByUser_InfoIdAndCourse_CourseIdIsNotNull(Integer infoId);

    // ✅ 특정 유저가 좋아요한 Review 목록 조회
    List<LikeTable> findByUser_InfoIdAndReview_ReviewIdIsNotNull(Integer infoId);

    // ✅ 특정 유저의 좋아요 데이터 삭제 (QnA, Course, Review 개별 삭제)
    void deleteByUser_InfoIdAndQna_QnaId(Integer infoId, Integer qnaId);
    void deleteByUser_InfoIdAndCourse_CourseId(Integer infoId, Integer courseId);
    void deleteByUser_InfoIdAndReview_ReviewId(Integer infoId, Integer reviewId);
}
