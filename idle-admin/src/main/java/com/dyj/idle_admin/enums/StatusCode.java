package com.dyj.idle_admin.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StatusCode {
    //http 默认状态码
    SUCCESS(200, "请求成功"),
    UNAUTHORIZED(401, "未授权，需要身份验证。"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到资源"),
    BAD_REQUEST(400, "请求无效"),
    //自定义状态码
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    RESOURCE_EXPIRATION(1002,"资源过期"),
    RESOURCE_EXISTS(1003,"资源已存在"),
    FORMAT_ERROR(1004,"格式错误"),
    FILE_IS_NULL(1005,"文件为空"),
    UPLOADING_EQUAL_FILE(1006,"正在上传相同的文件"),
    INEFFECTIVE_FILE(1007,"上传无效的文件"),
    SHARD_FILE_FAIL(1008,"文件分片上传失败"),
    DELETE_FILE_FAIL(1009,"删除文件失败"),

    OSS_NOT_FOUND(1010,"在数据库中没有找到Oss对象"),
    OSS_UPDATE_FAIL(1011,"在数据库中修改oss文件路径失败"),

    UPDATE_FAIL(1012,"修改数据库中的数据失败"),
    NULL(1013,"出现变量为空的情况"),
    UNABLE_TO_BUILD(1014,"无法生成"),
    INSERT_FAIL(1015,"插入数据库失败"),
    NO_PERMISSION(1016,"没有权限对数据库进行增删查改的一系列操作"),
    DELETE_FAIL(1017,"删除失败"),
    CHANGE_TO_BASE64_FAIL(1018,"转化文件为base64编码失败"),
    UPDATE_USER_FACE_FAIL(1019,"修改用户的面部数据失败"),
    INSERT_USER_FACE_FAIL(1020,"新增用户的面部特征失败"),
    SELECT_FAIL(1021,"查询异常"),
    FACE_RECOGNIZE_FAIL(1022,"人脸识别失败"),
    FACE_NO_RIGHT_SIGN(1023,"用户人脸识别失败"),
    SIGN_EXPIRED(1024,"签到过期"),
    REPETITIVE_OPERATION(1025,"重复的操作"),
    NO_RIGHT_OPERATION(1026,"无权操作");


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
    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}