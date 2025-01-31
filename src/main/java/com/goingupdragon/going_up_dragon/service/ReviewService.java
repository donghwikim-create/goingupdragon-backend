package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.ReviewsDTO;
import com.goingupdragon.going_up_dragon.entity.Reviews;
import com.goingupdragon.going_up_dragon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 특정 강의의 모든 리뷰 조회
    public List<ReviewsDTO> getReviewsByCourse(Integer courseId) { // ✅ 반환 타입 수정!
        List<Reviews> reviews = reviewRepository.findAllByCourse_CourseId(courseId);
        return reviews.stream().map(review -> new ReviewsDTO(
                review.getReviewId(),
                review.getUser().getInfoId(),  // ✅ 회원 info_id 추가
                review.getUser().getNickname(),  // ✅ 회원 닉네임 추가
                review.getRate(),
                review.getComment(),
                review.getReply(),
                review.getCreatedAt(),
                review.getReplyCreateAt()
        )).collect(Collectors.toList());
    }
}
