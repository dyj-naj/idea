package com.dyj.idle.config;

import com.dyj.idle.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 这里可以指定拦截的路径
                .excludePathPatterns("/user/login","/user/newToken",
                        "/home/banner","/user/verifyEmail","/user/register","/home/category","/home/goods","/alipay/pay","/alipay/notify","/home/seckill","/doc.html","/data/**"); // 排除不需要拦截的路径
    }
    //全局日期处理
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yy-MM-dd", Locale.getDefault()));
        registrar.registerFormatters(registry);
    }

}