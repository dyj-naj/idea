package com.dyj.idle;

import com.dyj.idle.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test

    public void test (){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU3ODM1ODk2NzMzMjI0NDAwMCwiZXhwIjoxNzI5Njc4MDM3LCJpYXQiOjE3Mjk2NzQ0Mzd9.i_YlYICbsCi8-yNO8NF7LXRtj92z6N075mwb9vpICfM";
        Long l = JwtUtil.extractId(token);
        System.out.println(l);

    }
    @Test
    public void generateToken() {
        // JWT头部分信息【Header】
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 载核【Payload】
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", "10001");
        payload.put("name","javaca");
        payload.put("admin",true);

        // 声明Token失效时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,300);// 300s

        // 生成Token
        String token = Jwts.builder()
                .setHeader(header)// 设置Header
                .setClaims(payload) // 设置载核
                .setExpiration(instance.getTime())// 设置生效时间
                .signWith(SignatureAlgorithm.HS256,"secret") // 签名,这里采用私钥进行签名,不要泄露了自己的私钥信息
                .compact(); // 压缩生成xxx.xxx.xxx
        System.out.println(token);
    }

    @Test
    public void getInfoByJwt() {
        // 生成的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4OTc1NyIsIm5hbWUiOiJqYXZhY2EiLCJhZG1pbiI6dHJ1ZSwiZXhwIjoxNzI5MTU3OTUxfQ.lrSu7HzLHrPTizYzVSYyRs16Ali70P0w7bTkVYDTiQ4";
        // 解析head信息
        JwsHeader jwsHeader = Jwts
                .parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getHeader();

        System.out.println(jwsHeader); // {typ=JWT, alg=HS256}
        System.out.println("typ:"+jwsHeader.get("typ"));

        // 解析Payload
        Claims claims =Jwts
                .parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims);// {sub=1234567890, name=John Doe, admin=true, exp=1663297431}
        System.out.println("admin:"+claims.get("admin"));

        // 解析Signature
        String signature = Jwts
                .parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getSignature();
        System.out.println(signature); // Ju5EzKBpUnuIRhDG1SU0NwMGsd9Jl_8YBcMM6PB2C20
    }

}
