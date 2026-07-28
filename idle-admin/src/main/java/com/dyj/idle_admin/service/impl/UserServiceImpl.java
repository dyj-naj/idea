package com.dyj.idle_admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.PublishInfoDTO;
import com.dyj.idle_admin.domain.dto.UserAddDTO;
import com.dyj.idle_admin.domain.dto.UserUpdateDTO;
import com.dyj.idle_admin.domain.po.SystemInfo;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.domain.query.UserPageQuery;
import com.dyj.idle_admin.domain.vo.UserInfoVO;
import com.dyj.idle_admin.listener.UserReadListener;
import com.dyj.idle_admin.mapper.UserMapper;
import com.dyj.idle_admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.utils.BeanUtils;
import com.dyj.idle_admin.utils.CollUtils;
import com.dyj.idle_admin.utils.IdWorker;
import com.dyj.idle_admin.utils.UniqueUserNameGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 处理登录逻辑
     * @param userName
     * @param password
     * @return
     */
    @Override
    public ResultData<User> doLogin(String userName, String password) {
        //1.根据账号和密码查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, userName)
                .eq(User::getPassword, password);

        User user = baseMapper.selectOne(queryWrapper);


        //2.判断用户是否存在
        if(user==null) return ResultData.Error("用户名或密码错误");
        if(user.getRole()>=5) return  ResultData.Error("用户没有权限登录");
        //3.用户存在就存入token，返回
        StpUtil.login(user.getId());
        return ResultData.Success(user,"登录成功").add("satoken", StpUtil.getTokenValue());
    }

    @Override
    public ResultData<String> doLogout(Long userId) {
        try {
            StpUtil.logout(userId);
            return ResultData.Success("退出成功");
        }catch (Exception e){
            return ResultData.Error("退出失败");
        }
    }

    @Override
    public ResultData<PageDTO<UserInfoVO>> getPages(UserPageQuery query) {

        // 1. 构建分页参数（注意处理默认值）
        Page<User> page = new Page<>(
                query.getPageNum() != null ? query.getPageNum() : 1, // 默认第1页
                query.getPageSize() != null ? query.getPageSize() : 6 // 默认6条/页
        );

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //封装条件
        if(query.getCondition()!=null){
            if(query.getCondition().getNickname()!=null&&!query.getCondition().getNickname().isEmpty()){
                queryWrapper.like(User::getNickname, query.getCondition().getNickname());
            }
            if(query.getCondition().getPhone()!=null&&!query.getCondition().getPhone().isEmpty()){
                queryWrapper.like(User::getPhone, query.getCondition().getPhone());
            }
            if(query.getCondition().getMailbox()!=null&&!query.getCondition().getMailbox().isEmpty()){
                queryWrapper.like(User::getMailbox, query.getCondition().getMailbox());
            }
            if(query.getCondition().getRole()!=null){
                queryWrapper.eq(User::getRole, query.getCondition().getRole());
            }
            if(query.getCondition().getIsForbidden()!=null){
                queryWrapper.eq(User::getIsForbidden, query.getCondition().getIsForbidden());
            }
            if(query.getCondition().getSex()!=null){
                queryWrapper.eq(User::getSex, query.getCondition().getSex());
            }
            if(query.getCondition().getAccount()!=null&&!query.getCondition().getAccount().isEmpty()){
                queryWrapper.like(User::getAccount, query.getCondition().getAccount());
            }
        }
        //执行分页查询
        System.out.println(queryWrapper);
        Page<User> userPage = this.page(page, queryWrapper);
        // 转换为 VO 对象（根据你的业务需求）
        List<User> records = userPage.getRecords();

        //处理空的情况
        if(CollUtils.isEmpty(records)){
            return ResultData.Success(PageDTO.empty(userPage));
        }
        List<UserInfoVO> userInfoVOList = BeanUtils.copyList(records, UserInfoVO.class);

        return ResultData.Success(PageDTO.of(userPage, userInfoVOList));
    }

    /**
     * 添加用户
     * @param userAddDTO
     * @return
     */
    @Override
    public ResultData<String> insertUser(UserAddDTO userAddDTO) {
        User user = BeanUtils.copyBean(userAddDTO, User.class);
        IdWorker idWorker=new IdWorker(0,0);
        user.setId(idWorker.nextId());
        user.setAccount(UniqueUserNameGenerator.generate());
        user.setPassword("123456");//默认密码

        this.save(user);
        return ResultData.Success("添加成功");

    }

    /**
     * 导入用户
     * @param file
     * @return
     */
    @Override
    public ResultData<String> importUser(MultipartFile file) {
        try {
            UserReadListener listener=new UserReadListener(this);
            EasyExcel.read(file.getInputStream(), User.class,listener).sheet(0).headRowNumber(1).doRead();
            return ResultData.Success("成功导入"+listener.getSuccessCount()+"条数据，失败"+listener.getFailedCount()+"条数据");
        }catch (Exception e){
            return ResultData.Success("导入失败，请稍后再试");
        }
    }

    @Override
    public ResultData<String> changeStatus(Long userId) {
        // 创建更新条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("is_forbidden = 1 - is_forbidden")  // 直接计算新状态
                .eq("id", userId);         // 根据用户ID更新

        // 执行更新操作
        boolean success = this.update(updateWrapper);

        // 处理返回结果
        if (success) {
            return ResultData.Success("用户状态切换成功");
        } else {
            return ResultData.Error( "用户不存在或状态未改变");
        }
    }

    @Override
    public ResultData<String> updateUser(UserUpdateDTO dto) {
        try {
            //封装好更新的对象
            User user = baseMapper.selectById(dto.getUserId());
            user.setPhone(dto.getPhone());
            user.setNickname(dto.getNickname());
            user.setSex(dto.getSex());
            user.setMailbox(dto.getMailbox());
            user.setRole(dto.getRole());
            boolean b = this.updateById(user);
            if(b){
                return ResultData.Success("更新成功");
            }else{
                return ResultData.Error("更新失败");
            }
        }catch (Exception e){
            return ResultData.Error("更新失败");
        }
    }

    @Override
    public ResultData<String> resetPassword(Long userId) {
        try {
            User user = baseMapper.selectById(userId);
            user.setPassword("123456");
            boolean b = this.updateById(user);
            if(b){
                return ResultData.Success("重置成功");
            }else{
                return ResultData.Error("重置失败");
            }
        }catch (Exception e){
            return ResultData.Error("重置失败");
        }
    }

    @Override
    public ResultData<String> deleteUser(Long userId) {
        try {
            boolean b = this.removeById(userId);
            if(b){
                return ResultData.Success("删除成功");
            }else{
                return ResultData.Error("删除失败");
            }
        }catch (Exception e){
            return ResultData.Error("删除失败");
        }
    }


}
