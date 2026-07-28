package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.PublishInfoDTO;
import com.dyj.idle_admin.domain.dto.UserAddDTO;
import com.dyj.idle_admin.domain.dto.UserUpdateDTO;
import com.dyj.idle_admin.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyj.idle_admin.domain.query.UserPageQuery;
import com.dyj.idle_admin.domain.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-09
 */
public interface IUserService extends IService<User> {

    ResultData<User> doLogin(String userName, String password);

    ResultData<String> doLogout(Long userId);

    ResultData<PageDTO<UserInfoVO>> getPages(UserPageQuery query);

    ResultData<String> insertUser(UserAddDTO userAddDTO);

    ResultData<String> importUser(MultipartFile file);

    ResultData<String> changeStatus(Long userId);

    ResultData<String> updateUser(UserUpdateDTO user);

    ResultData<String> resetPassword(Long userId);

    ResultData<String> deleteUser(Long userId);

}
