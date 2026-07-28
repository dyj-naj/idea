package com.dyj.idle_admin.common;


import com.dyj.idle_admin.enums.StatusCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果集合
 * @author 杜英杰
 * */
@Data
public class ResultData<T> {
    // 返回的信息描述
    String msg;
    /*
    * 返回状态码 StatusCode.code;
    * */
    StatusCode code;

    // 实际数据
    T data;

    private Map map = new HashMap(); //动态数据

    public ResultData(String msg, StatusCode code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public ResultData<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    // 构建函数
    public ResultData(StatusCode code) {
        setCode(code);
    }

    public ResultData(String msg) {
        this.msg = msg;
    }

    public ResultData() {
        this.code = StatusCode.SUCCESS;
    }

    // 工厂方法
    public static <E> ResultData<E> Error() {
        return new ResultData<>("服务器错误", StatusCode.INTERNAL_SERVER_ERROR, null);
    }
    public static <E> ResultData<E> Error(String msg) {
        return new ResultData<>(msg, StatusCode.INTERNAL_SERVER_ERROR, null);
    }
    public static <E> ResultData<E> Error(String msg,E data) {
        return new ResultData<>(msg, StatusCode.INTERNAL_SERVER_ERROR, data);
    }
    public static <E> ResultData<E> Error(String msg,StatusCode status) {
        return new ResultData<>(msg, status, null);
    }
    public static <E> ResultData<E> Success(E data) {
        return new ResultData<>("成功", StatusCode.SUCCESS, data);
    }
    public static <E> ResultData<E> Success(E data,String msg) {
        return new ResultData<>(msg, StatusCode.SUCCESS, data);
    }
}