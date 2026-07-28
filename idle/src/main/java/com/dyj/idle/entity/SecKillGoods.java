package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SecKillGoods {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;//开始时间
    private String secKillName;//该场秒杀的名字
    private Integer secKillNum;//瓜分多少份
    private Integer secKillValue;//每份多少价值
    private Integer startCountDown;
    private Integer endCountDown;

}
