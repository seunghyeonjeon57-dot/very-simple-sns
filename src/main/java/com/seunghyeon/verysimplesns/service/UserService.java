package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.FindUserRequest;
import com.seunghyeon.verysimplesns.dto.request.SignUpRequest;
import com.seunghyeon.verysimplesns.dto.request.UpdatedUserRequest;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User create(SignUpRequest request){
        User user = User.builder().email(request.email())
                .userName(request.userName())
                .password(request.password())
                .nickName(request.nickName()).build();
        return userRepository.save(user);
    }

    public User findUser(FindUserRequest request){
        return userRepository.findByUsername(request.userName())
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    public User updateUser(UUID userId, UpdatedUserRequest request){
        User user  = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        user.update(request.email(),request.nickName());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }
}
