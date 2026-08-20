package com.seunghyeon.verysimplesns.config;

import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private final String secret;

    @Value("${jwt.expiration}")
    private final long expiration;

    private SecretKey key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String createToken(UUID userId){
        Date newDate = new Date(System.currentTimeMillis()+expiration);
        String token = Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(newDate)
                .signWith(key)
                .compact();
        return token;
    }


    public UUID getUserId(String token){
        if(token == null){
            throw new SimpleSnsException("토큰이 존재하지않습니다." ,HttpStatus.UNAUTHORIZED);
        }

        try{
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String userId = claims.getSubject();

        UUID id = UUID.fromString(userId);
        return id;}
        catch (JwtException e){
            throw new SimpleSnsException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
