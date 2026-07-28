package com.dyj.idle_admin.config;

import com.dyj.idle_admin.common.ResultData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *全局异常处理器
 */
@RestControllerAdvice
public class AppExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResultData handler(Exception e) {
//        System.out.println(e.getMessage());
//        return ResultData.Success(e.getMessage());
//    }
}
