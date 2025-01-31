package com.goingupdragon.going_up_dragon.dto;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewsDTO {
    private Integer reviewId;
    private Integer infoId;  // ✅ 회원 ID 추가
    private String nickname; // ✅ 회원 닉네임 추가
    private float rate;
    private String comment;
    private String reply;
    private LocalDateTime createdAt;
    private LocalDateTime replyCreatedAt;
}
