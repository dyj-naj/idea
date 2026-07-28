package com.dyj.idle.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class UploadTag {
    public static String getUserHeadImageMD5(Long id,String path){
        String res="用户"+id.toString()+"的头像图片路径"+path;
        System.out.println(res);
        return DigestUtils.md5DigestAsHex(res.getBytes())+".png";
    }
    public static String getUserGoodsMD5(Long id){
        String res="用户"+id.toString()+"的商品图片："+ UUID.randomUUID().toString();
        System.out.println(res);
        return DigestUtils.md5DigestAsHex(res.getBytes())+".png";
    }
    public static String extractFileName(String url) {
        int lastIndexOfSlash = url.lastIndexOf('/');
        if (lastIndexOfSlash != -1) {
            return url.substring(lastIndexOfSlash + 1);
        } else {
            return url; // 如果没有找到斜杠，返回整个字符串
        }
    }
}
