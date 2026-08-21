package com.seunghyeon.verysimplesns.filter;
import com.seunghyeon.verysimplesns.config.JwtProvider;
import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String header =request.getHeader("Authorization");

        if(header != null &&  header.startsWith("Bearer ")){
            String token= header.substring(7);
            try{UUID userId = jwtProvider.getUserId(token);
            UsernamePasswordAuthenticationToken userToken =
                    new UsernamePasswordAuthenticationToken(userId,null, List.of());
            SecurityContextHolder.getContext().setAuthentication(userToken);}
            catch (SimpleSnsException e){
                log.debug("불가피한 예외 발생",e);
            }
        }
    filterChain.doFilter(request,response);
    }
}
