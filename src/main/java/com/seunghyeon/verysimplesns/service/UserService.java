package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.SignUpRequest;
import com.seunghyeon.verysimplesns.dto.request.UpdatedUserRequest;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import com.seunghyeon.verysimplesns.validate.BannedWordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(SignUpRequest request){
        if(BannedWordValidator.isBanned(request.nickName())){
            throw new SimpleSnsException("금칙어가 들어가있습니다." , HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(request.email())){
            throw new SimpleSnsException("중복된 이메일입니다", HttpStatus.CONFLICT);
        }
        if(userRepository.existsByUserName(request.userName())){
            throw new SimpleSnsException("중복된 아이디입니다.",HttpStatus.CONFLICT);
        }
        if(userRepository.existsByNickName(request.nickName())){
            throw new SimpleSnsException("중복된 닉네임입니다.",HttpStatus.CONFLICT);
        }
        String encode = passwordEncoder.encode(request.password());
        User user = User.builder().email(request.email())
                .userName(request.userName())
                .password(encode)
                .nickName(request.nickName()).build();
        return userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public User findUser(String userName){
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new SimpleSnsException("유저를 찾을 수 없습니다.",HttpStatus.NOT_FOUND));
    }

    public User updateUser(UUID userId, UpdatedUserRequest request){
        User user= findById(userId);
        user.update(request.email(),request.nickName());
        return user;
            }

    public void deleteUser(UUID id){
        findById(id);
        userRepository.deleteById(id);
    }



    public User findById(UUID id){
        User user  = userRepository.findById(id)
                .orElseThrow(()->new SimpleSnsException("유저를 찾을 수 없습니다.",HttpStatus.NOT_FOUND));
        return user;
    }
}


