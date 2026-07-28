package com.dyj.idle_admin.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum SortStatus {
    CREATE_TIME_ASC(1, "按照创建时间升序排序"),
    CREATE_TIME_DESC(2, "按照创建时间降序排序"),
    PRICE_ASC(3, "按照价格升序排序"),
    PRICE_DESC(4, "按照价格降序排序");


    /**
     * 状态码的数字值。
     */
    @JsonValue
    private final int code;
    /**
     * 状态码对应的消息文本。
     */
    private final String message;

    /**
     * 构造一个状态码枚举实例。
     *
     * @param code 状态码的数字值
     * @param message 状态码对应的消息文本
     */
    SortStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}