package com.goingupdragon.going_up_dragon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String token;  // 🔹 JWT 토큰 필드 추가
}
