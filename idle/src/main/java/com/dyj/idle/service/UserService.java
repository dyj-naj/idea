package com.dyj.idle.service;

import com.dyj.idle.entity.FeedbackDTO;
import com.dyj.idle.entity.User;
import com.dyj.idle.mapper.UserMapper;
import com.dyj.idle.utils.UploadTag;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OssService ossService;

    public void insert(Long id,String mailbox,String account,String password){
        userMapper.insert(id,mailbox,account,password);
    }

    public User getUserInfoByLogin(String account,String password){
        return userMapper.getUserInfoByLogin(account,password);
    }

    public User getUserInfoById(Long id){
        return userMapper.getUserInfoById(id);
    }//通过id查找账号

    public User getUserInfoByAccount(String account){
        return userMapper.getUserInfoByAccount(account);
    }//通过账号查找用户信息

    public User verifyEmailIsExist(String mailbox){return userMapper.verifyEmailIsExist(mailbox);}//验证邮箱存在

    public void saveUserInfo(Integer sex,String birthday,String introduction,Long id,String nickname,String phone){
        userMapper.saveUserInfo(sex,birthday,introduction,id,nickname,phone);
    }//保存用户信息

    public void changeImage(String headUrl,Long id){
        //删除老头像
        User userInfo = userMapper.getUserInfoById(id);
        if(userInfo.getHeadUrl()!=null){
            //删除
            ossService.deleteImage(UploadTag.extractFileName(userInfo.getHeadUrl()));
        }
        //保存新头像
        userMapper.changeImage(headUrl,id);
    }//更新用户头像

    //查看收藏状态
    public boolean isCollect(Long userId,Long goodsId){
        Long collect = userMapper.isCollect(userId, goodsId);
        if(collect!=null){
            return  true;
        }else {
            return false;
        }
    }
    //更换收藏状态
    public void changeCollect(Long userId,Long goodsId,boolean tag){
        if(tag){
            userMapper.delCollect(userId,goodsId);
        }else{
            userMapper.insCollect(userId,goodsId);
        }
    }

    public void changeAttention(Long userId,Long fansId,int tag){
        if(tag==1){
            userMapper.delInUserFans(userId,fansId);
        }else{
            userMapper.insInUserFans(userId,fansId);
        }
    }
    public boolean checkAttention(Long userId,Long fansId){
        Long l = userMapper.checkAttention(userId, fansId);
        if(l!=null){
            return true;
        }else{
            return false;
        }
    }
         //得到关注数
    public Integer getAttentionNum(Long userId){
        return userMapper.getAttentionNum(userId);
    }
    public Integer getFansNum(Long userId){
        return userMapper.getFansNum(userId);
    }


    //更新用户的小猪币
    public void changeCoin(Long userId,Integer changeNum){
        userMapper.changeCoin(userId,changeNum);
    }

    public void saveFeedback(FeedbackDTO dto) {
        userMapper.saveFeedback(dto.getUserId(),dto.getUserAccount(),dto.getContent(),dto.getStar());
    }
}
