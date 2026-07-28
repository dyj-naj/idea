package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long cwId;

    private Integer type;

    private Long sendId;

    private Long receiveId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Date sendTime;
}
