package com.example.todo_friend.global.utils;

import com.example.todo_friend.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Map;

@Component
public class JwtUtils {
    private final Long expiration;
    private final SecretKey secretKey;

    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration
    ) {
        this.expiration = expiration;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Map<String, Object> parseToken(String token) {
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();

        Long userId = claims.get("id", Long.class);
        String userNickname = claims.get("nickname", String.class);
        String userImage = claims.get("image", String.class);

        return Map.of(
                "id", userId,
                "nickname", userNickname,
                "image", userImage
        );
    }

    public User getUserInfoFromToken(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();

        Long userId = payload.get("id", Long.class);
        String userNickname = payload.get("nickname", String.class);
        String userImage = payload.get("image", String.class);
        return new User(userId, userNickname, userImage);
    }
}