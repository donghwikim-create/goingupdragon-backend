package com.goingupdragon.going_up_dragon.service;


import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.dto.ReviewsDTO;
import com.goingupdragon.going_up_dragon.dto.myPage.MyPageInstructorDTO;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.enums.Enums;
import com.goingupdragon.going_up_dragon.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {

    private final CourseRepository courseRepository;
    private final QnARepository qnARepository;
    private final ReviewRepository reviewRepository;
    private final UserInfoRepository userInfoRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LikeTableRepository likeTableRepository;

    public MyPageService(CourseRepository courseRepository, QnARepository qnARepository, ReviewRepository reviewRepository, UserInfoRepository userInfoRepository, EnrollmentRepository enrollmentRepository, LikeTableRepository likeTableRepository){
        this.courseRepository = courseRepository;
        this.reviewRepository = reviewRepository;
        this.qnARepository = qnARepository;
        this.userInfoRepository = userInfoRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.likeTableRepository = likeTableRepository;
    }

    public MyPageInstructorDTO GetMyPageInstructorDTO(Integer infoId)
    {
        Enums.Role role = userInfoRepository.findByInfoId(infoId).getRole();

        if (role == Enums.Role.instructor) {
            // 1. 기본 정보 가져오기
            UserInfo instructor = userInfoRepository.findById(infoId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));

            // 2. 강사가 개설한 강의 목록 가져오기
            List<CourseDTO> courseList = courseRepository.findInstructorCourses(infoId)
                    .stream()
                    .map(course -> new CourseDTO( // Course → CourseDTO 변환
                            course.getCourseId(),
                            course.getCourseTitle(),
                            course.getShortDescription(),
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            course.getMainCategory().getCategoryName(),
                            course.getMainCategory().getCategoryId(),
                            course.getSubCategory().getCategoryName(),
                            course.getSubCategory().getCategoryId(),
                            course.getLevel(),
                            course.getLanguage(),
                            course.getPrice(),
                            List.of(), // subjectTagIds (필요하면 설정)
                            List.of(), // subjectTagNames (필요하면 설정)
                            course.getStartDate(),
                            reviewRepository.findReviewCount(course.getCourseId()), // 리뷰 개수
                            reviewRepository.findReviewRate(course.getCourseId()), // 평균 평점
                            enrollmentRepository.findEnrollmentCountForCourse(course.getCourseId()), // 수강생 수
                            course.getDuration()
                    ))
                    .collect(Collectors.toList());

            // 3. 강사의 강의에 달린 리뷰 가져오기
            List<ReviewsDTO> reviewList = reviewRepository.findInstructorReviews(infoId)
                    .stream()
                    .map(review -> new ReviewsDTO(
                            review.getReviewId(),
                            review.getUser().getInfoId(),
                            review.getUser().getNickname(),
                            review.getRate(),
                            review.getComment(),
                            review.getReply(),
                            review.getCreatedAt().toString(),
                            review.getReplyCreateAt() != null ? review.getReplyCreateAt().toString() : null
                    ))
                    .collect(Collectors.toList());

            // 4. 강사의 강의에 달린 QnA 가져오기
            List<QnADTO> qnaList = qnARepository.findInstructorQnAs(infoId)
                    .stream()
                    .map(qna -> new QnADTO(
                            qna.getQnaId(),
                            qna.getUser().getInfoId(),
                            qna.getUser().getNickname(),
                            qnARepository.countQnAsByStudentId(qna.getUser().getInfoId()), // 학생이 작성한 QnA 개수
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            qna.getCourse().getCourseId(),
                            qna.getCourse().getCourseTitle(),
                            qna.getCourse().getShortDescription(),
                            qna.getTitle(),
                            qna.getContent(),
                            qna.getCustomTag1(),
                            qna.getCustomTag2(),
                            qna.getCustomTag3(),
                            qna.getCreatedAt(),
                            qna.getViewCount(),
                            likeTableRepository.countLikesByQnAId(qna.getQnaId()),
                            qnARepository.countRepliesByQnAId(qna.getQnaId()) // QnA의 답변 개수
                    ))
                    .collect(Collectors.toList());

            // 5. DTO 생성
            return  new MyPageInstructorDTO(
                    instructor.getInfoId(),
                    instructor.getNickname(),
                    instructor.getBio(),
                    courseList,
                    reviewList,
                    qnaList
            );
        }

        return null;
    }
}
