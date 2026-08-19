package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.SignUpRequest;
import com.seunghyeon.verysimplesns.dto.request.UpdatedUserRequest;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(SignUpRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("중복된 이메일입니다");
        }
        if(userRepository.existsByUsername(request.userName())){
            throw new RuntimeException("중복된 아이디입니다.");
        }
        if(userRepository.existsByNickname(request.nickName())){
            throw new RuntimeException("중복된 닉네임입니다.");
        }
        String encode = passwordEncoder.encode(request.password());
        User user = User.builder().email(request.email())
                .userName(request.userName())
                .password(encode)
                .nickName(request.nickName()).build();
        return userRepository.save(user);

    }

    public User findUser(String userName){
        return userRepository.findByUsername(userName)
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
