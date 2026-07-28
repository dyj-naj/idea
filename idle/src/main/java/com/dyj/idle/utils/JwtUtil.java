package com.dyj.idle.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "dyjdyjdyjdyjdyjdyj"; // 替换为你的密钥

    // 生成 JWT
    public static String generateToken(Long id,long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", id); // 将用户名存入载荷中

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 从 JWT 中提取用户名
    public static Long extractId(String token) {
        return extractAllClaims(token).get("sub", Long.class);
    }

    // 从 JWT 中提取所有声明
    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 Token 是否有效
    public static boolean isTokenValid(String token, Long id) {
        final Long userId = extractId(token);
        return (!userId.equals(id) && !isTokenExpired(token));
    }

    // 检查 Token 是否过期
    public static boolean isTokenExpired(String token) {
        try {
            // 解析token并获取Claims对象
            Claims claims = extractAllClaims(token);
            // 检查token是否过期
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 如果token已过期，返回true
            return true;
        } catch (Exception e) {
            // 如果在解析token过程中发生其他异常，返回false
            // 这里可以记录日志或进行其他错误处理
            return true;
        }
    }

}
