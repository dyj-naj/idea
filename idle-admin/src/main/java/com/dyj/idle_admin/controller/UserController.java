package com.dyj.idle_admin.controller;


import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.MapUtils;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.PublishInfoDTO;
import com.dyj.idle_admin.domain.dto.UserAddDTO;
import com.dyj.idle_admin.domain.dto.UserUpdateDTO;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.domain.query.UserPageQuery;
import com.dyj.idle_admin.service.ISystemInfoService;
import com.dyj.idle_admin.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "用户管理")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final ISystemInfoService systemInfoService;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @Operation(summary = "登录")
    @PostMapping("/doLogin")
    public ResultData login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        return userService.doLogin(userName,password);
    }

    /**
     * 退出登录
     * @param userId
     * @return
     */
    @Operation(summary = "退出登录")
    @GetMapping("/doLogout")
    public ResultData logout(@RequestParam("userId") Long userId){
        return userService.doLogout(userId);
    }

    @Operation(summary = "分页查询用户")
    @PostMapping("/list")
    public ResultData page(@RequestBody UserPageQuery query){
        return userService.getPages(query);
    }

    //--------------------------------以下的接口只有用户管理员和超级管理员有权限调用--------------------------------
    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public ResultData addUser(@RequestBody UserAddDTO userAddDTO){
        return userService.insertUser(userAddDTO);
    }


    @Operation(summary = "导入用户")
    @PostMapping("/import")
    public ResultData importUser(MultipartFile file){
        return userService.importUser(file);
    }

    @Operation(summary = "导出用户")
    @PostMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 1. 设置响应头（关键！）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("闲小猪用户列表", "UTF-8").replaceAll("\\+", "%20"); // 处理空格问题
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 2. 获取数据（示例从服务层获取）
        List<User> userList = userService.list();

        // 3. 通过 EasyExcel 写入响应流
        try (OutputStream out = response.getOutputStream()) {
            EasyExcel.write(out, User.class)
                    .sheet("用户信息")
                    .doWrite(userList);
        } catch (Exception e) {
            // 4. 异常处理（可选：记录日志或返回错误信息）
            throw new RuntimeException("导出失败", e);
        }

    }

    /**
     * 更改用户状态
     * @param userId
     * @return
     */
    @Operation(summary = "更改用户状态")
    @GetMapping("/changStatus")
    public ResultData changeStatus(@RequestParam("userId") Long userId){
        return userService.changeStatus(userId);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Operation(summary = "修改用户信息")
    @PostMapping("/updateUser")
    public ResultData updateUser(@RequestBody UserUpdateDTO user){
        return userService.updateUser(user);
    }

    /**
     * 重置密码
     * @param userId
     * @return
     */
    @Operation(summary = "重置密码")
    @GetMapping("/resetPassword")
    public ResultData resetPassword(@RequestParam("userId") Long userId){
        return userService.resetPassword(userId);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/delete")
    public ResultData deleteUser(@RequestParam("userId") Long userId){
        return userService.deleteUser(userId);
    }

    /**
     * 发送积分变动信息
     * @param dto
     * @return
     */
    @Operation(summary = "发送积分变动信息")
    @PostMapping("/sendCoinChangeInfo")
    public ResultData sendCoinChangeInfo(@RequestBody PublishInfoDTO dto){
        return systemInfoService.sendCoinChangeInfo(dto);
    }


}
