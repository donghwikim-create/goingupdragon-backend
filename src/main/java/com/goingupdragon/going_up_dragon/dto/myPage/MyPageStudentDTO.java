package com.goingupdragon.going_up_dragon.dto.myPage;

import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.dto.ReviewsDTO;
import com.goingupdragon.going_up_dragon.dto.SubjectTagsDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageStudentDTO {
    private Integer infoId;
    private String nickName;
    private String bio;
    private List<CourseDTO> courseList;
    private List<ReviewsDTO> reviewList;
    private List<CourseDTO> courseLikeList;
    private List<SubjectTagsDTO> subjectTagList;
}
