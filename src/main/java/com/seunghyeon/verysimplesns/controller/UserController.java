package com.seunghyeon.verysimplesns.controller;

import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.SignUpRequest;
import com.seunghyeon.verysimplesns.dto.request.UpdatedUserRequest;
import com.seunghyeon.verysimplesns.dto.response.FindUserResponse;
import com.seunghyeon.verysimplesns.dto.response.UserResponse;
import com.seunghyeon.verysimplesns.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(
            @Valid  @RequestBody SignUpRequest request
            ){
        User user = userService.create(request);
        UserResponse userResponse = UserResponse.from(user);
        URI location = URI.create("/users/" + user.getId());
        return ResponseEntity.created(location).body(userResponse);
    }

    @GetMapping("/find")
    public ResponseEntity<FindUserResponse> find(
            @RequestParam String userName
            ){
        User user = userService.findUser(userName);
        FindUserResponse response = FindUserResponse.from(user);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/update")
    public ResponseEntity<UserResponse> update(
            @AuthenticationPrincipal
            UUID userId,
            @Valid @RequestBody
            UpdatedUserRequest request
            ){
        User user = userService.updateUser(userId,request);
        UserResponse userResponse = UserResponse.from(user);
        return ResponseEntity.status(200).body(userResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal
             UUID userId
    ){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }




}
