package com.dyj.idle_admin.utils;


public interface Convert<R,T>{
    void convert(R origin, T target);
}