package com.dyj.idle.interceptor;

import com.dyj.idle.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static com.dyj.idle.utils.JwtUtil.isTokenExpired;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return false;
        }
        System.out.println("进行token验证");
        System.out.println(token.substring(7));
        if(JwtUtil.isTokenExpired(token.substring(7))){//验证是否过期或者无效
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }else{
            System.out.println("未过期");
        }
        // 这里可以添加更多的Token验证逻辑
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里做一些请求处理后的操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后的操作
    }
}