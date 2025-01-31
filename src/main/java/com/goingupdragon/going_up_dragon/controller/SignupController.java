package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.SignupRequestDTO;
import com.goingupdragon.going_up_dragon.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO request) {
        signupService.registerUser(request.getEmail(), request.getPassword(), request.getPhoneNumber());
        return ResponseEntity.ok("회원가입 성공!");
    }
}

