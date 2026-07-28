package com.dyj.idle.mapper;


import com.dyj.idle.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(id,account,mailbox,password,is_forbidden,role) values (#{id},#{account},#{mailbox},#{password},0,5)")
    void insert(Long id,String mailbox,String account,String password);//插入数据

    @Select("select * from user where (account = #{account} or mailbox=#{account}) and password = #{password}")
    User getUserInfoByLogin(String account, String password);//通过账号密码查找用户信息

    @Select("select * from user where id = #{id} ")
    User getUserInfoById(Long id);//通过id查找用户信息

    @Select("select * from user where account = #{account} ")
    User getUserInfoByAccount(String account);//通过账号查找用户信息
    @Select("select * from user where mailbox=#{mailbox}")
    User verifyEmailIsExist(String mailbox);//验证邮箱是否存在

    @Update("update user set sex=#{sex},birthday=#{birthday},introduction=#{introduction},nickname=#{nickname},phone=#{phone} where id=#{id}")
    void saveUserInfo(Integer sex,String birthday,String introduction,Long id,String nickname,String phone);//保存用户基础信息

    @Update("update user set head_url=#{headUrl} where id=#{id}")
    void changeImage(String headUrl,Long id);//更新用户头像

    //查看用户是否收藏
    @Select("SELECT user_id from user_collection where user_id=#{userId} and goods_id=#{goodsId}")
    Long isCollect(Long userId,Long goodsId);


    @Delete("DELETE from user_collection where user_id=#{userId} and goods_id=#{goodsId}")
    void delCollect(Long userId,Long goodsId);

    @Insert("INSERT INTO user_collection values(#{userId},#{goodsId})")
    void insCollect(Long userId,Long goodsId);

    @Delete("delete from user_fans where user_id=#{userId} and fans_id=#{fansId}")
    void delInUserFans(Long userId,Long fansId);

    @Insert("insert into user_fans(user_id,fans_id) values(#{userId},#{fansId})")
    void insInUserFans(Long userId,Long fansId);

    @Select("select user_id from user_fans where user_id=#{userId} and fans_id=#{fansId}")
    Long checkAttention(Long userId,Long fansId);

    //得到关注数

    @Select("SELECT COUNT(user_id) FROM user_fans WHERE fans_id=#{userId}")
    Integer getAttentionNum(Long userId);

    //得到粉丝数
    @Select("SELECT COUNT(user_id) FROM user_fans WHERE user_id=#{userId}")
    Integer getFansNum(Long userId);

    @Update("update user set coin_cnt=coin_cnt-#{changeNum} where id=#{userId}")
    void changeCoin(Long userId,Integer changeNum);

    @Insert("insert into system_feedback(publisher_id,publisher_account,content,star) values(#{userId},#{userAccount},#{content},#{star})")
    void saveFeedback(Long userId, String userAccount, String content, Integer star);
}
