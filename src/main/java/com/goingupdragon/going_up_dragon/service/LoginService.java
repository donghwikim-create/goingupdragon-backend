package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.LoginRequestDTO;
import com.goingupdragon.going_up_dragon.dto.LoginResponseDTO;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
import com.goingupdragon.going_up_dragon.repository.UserSecurityRepository;
import com.goingupdragon.going_up_dragon.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // 🔹 JWT 유틸 추가
    private final UserInfoRepository userInfoRepository;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // 1️⃣ 사용자 정보 조회
        UserSecurity userSecurity = userSecurityRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("유효하지 않은 사용자입니다."));

        // 2️⃣ 비밀번호 검증 (암호화된 비밀번호와 비교)
        if (!passwordEncoder.matches(loginRequest.getPassword(), userSecurity.getPassword())) { // ✅ 암호화된 비밀번호 비교
            return new LoginResponseDTO(false, "비밀번호가 일치하지 않습니다.", null);
        }

        // 3️⃣ JWT 토큰 생성 및 반환
        String token = jwtUtil.generateToken(userSecurity.getEmail());
        return new LoginResponseDTO(true, "로그인 성공", token);
    }
}

