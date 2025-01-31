package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.LoginRequestDTO;
import com.goingupdragon.going_up_dragon.dto.LoginResponseDTO;
import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import com.goingupdragon.going_up_dragon.repository.UserSecurityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder) {
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<UserSecurity> userOptional = userSecurityRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            UserSecurity user = userOptional.get();

            // 비밀번호 확인
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new LoginResponseDTO(true, "로그인 성공!", "JWT-TOKEN");
            } else {
                return new LoginResponseDTO(false, "비밀번호가 일치하지 않습니다.", null);
            }
        } else {
            return new LoginResponseDTO(false, "이메일이 존재하지 않습니다.", null);
        }
    }
}
