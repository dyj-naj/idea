package com.dyj.idle_admin.controller;

import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.service.ISystemDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-12
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system")
@Slf4j
@RequiredArgsConstructor
public class SystemController {
    private final ISystemDataService systemDataService;
    @GetMapping("/")
    public ResultData getSystemData(){
        return systemDataService.getSystemData();
    }

    /**
     * 导入系统分类信息
     */
    public ResultData importSystemData(){
        return null;
    }



}
