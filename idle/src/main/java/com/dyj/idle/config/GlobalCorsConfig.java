//package com.dyj.idle.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//
//@Configuration
//public class GlobalCorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        // 1. 添加 CORS 配置信息
//        CorsConfiguration config = new CorsConfiguration();
//        // 放行
//        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", "chrome-extension://*","http://localhost:5174"));
//        // 是否发送 Cookie
//        config.setAllowCredentials(true);
//        // 放行所有请求方式
//        config.addAllowedMethod("*");
//        // 放行所有原始请求头部信息
//        config.addAllowedHeader("*");
//        // 暴露所有头部信息
//        config.addExposedHeader("*");
//
//        // 2. 添加映射路径
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", config);
//
//        // 3. 返回新的 CorsFilter 并传入配置
//        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
//        return corsFilter;
//    }
//}