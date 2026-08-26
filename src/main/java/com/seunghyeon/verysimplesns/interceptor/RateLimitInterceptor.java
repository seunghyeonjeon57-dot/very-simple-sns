package com.seunghyeon.verysimplesns.interceptor;


import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import com.seunghyeon.verysimplesns.global.RateLimitStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimitStore store;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String method = request.getMethod();
        boolean allowed;
        if (method.equals("GET")) {
            allowed=store.tryIncrementRead(userId);
        } else {
            allowed=store.tryIncrementWrite(userId);
        }if(!allowed)
            throw new SimpleSnsException("잠시 후 시도해주세요", HttpStatus.TOO_MANY_REQUESTS);



        return true;
    }

}
