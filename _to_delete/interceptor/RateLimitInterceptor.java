package com.seunghyeon.verysimplesns.interceptor;

import com.seunghyeon.verysimplesns.ratelimit.ApiAction;
import com.seunghyeon.verysimplesns.ratelimit.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * JwtAuthenticationFilter가 SecurityContext에 인증 정보를 세팅한 "이후",
 * 컨트롤러 메서드가 실행되기 "직전"에 동작한다 (서블릿 필터가 아니라 스프링 MVC 핸들러 인터셉터).
 * 그래서 preHandle 시점에는 이미 SecurityContextHolder에서 userId를 꺼내올 수 있다.
 */
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;
    private final ApiAction action;

    public RateLimitInterceptor(RateLimiter rateLimiter, ApiAction action) {
        this.rateLimiter = rateLimiter;
        this.action = action;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UUID userId)) {
            // 인증 안 된 요청은 어차피 Security 설정에서 401로 막히므로 여기서는 그냥 통과시킴
            return true;
        }

        rateLimiter.checkAndCount(userId, action);
        return true;
    }
}
