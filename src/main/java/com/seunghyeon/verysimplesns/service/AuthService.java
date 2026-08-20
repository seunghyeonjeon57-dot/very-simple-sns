package com.seunghyeon.verysimplesns.service;


import com.seunghyeon.verysimplesns.config.JwtProvider;
import com.seunghyeon.verysimplesns.domain.User;
import com.seunghyeon.verysimplesns.dto.request.SignInRequest;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider provider;
    private final PasswordEncoder passwordEncoder;


    public String login(SignInRequest request){
       User user= userRepository.findByEmail(request.email())
                .orElseThrow(()-> new SimpleSnsException("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED));

        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new SimpleSnsException("로그인에 실패하였습니다.",HttpStatus.UNAUTHORIZED);

        }

        String token=provider.createToken(user.getId());
        return token;
    }

}
