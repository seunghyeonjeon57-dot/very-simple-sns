package com.seunghyeon.verysimplesns.controller;

import com.seunghyeon.verysimplesns.dto.request.SignInRequest;
import com.seunghyeon.verysimplesns.dto.response.LoginResponse;
import com.seunghyeon.verysimplesns.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody SignInRequest request){
     String token=   authService.login(request);
     LoginResponse loginResponse =  new LoginResponse(token);
     return ResponseEntity.ok(loginResponse);
    }
}
