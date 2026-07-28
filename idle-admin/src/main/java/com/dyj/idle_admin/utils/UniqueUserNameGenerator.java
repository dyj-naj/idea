package com.dyj.idle_admin.utils;

import java.security.SecureRandom;

public class UniqueUserNameGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String PREFIX = "xxz-";
    private static final int TOTAL_LENGTH = 10;
    private static final int NUMBER_LENGTH = TOTAL_LENGTH - PREFIX.length();

    public static String generate() {
        StringBuilder sb = new StringBuilder(PREFIX);
        // 生成6位随机数字（范围0-9）
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            sb.append(random.nextInt(10));  // 生成0-9的随机数
        }
        return sb.toString();
    }
}