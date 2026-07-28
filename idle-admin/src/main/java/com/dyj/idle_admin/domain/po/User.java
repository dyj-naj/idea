package com.dyj.idle_admin.domain.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.dyj.idle_admin.config.LongToStringConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author dyj
 * @since 2025-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @ExcelProperty(value = "用户ID",converter = LongToStringConverter.class)
    @TableId(value = "id", type = IdType.INPUT)
    @ColumnWidth(20)
    private Long id;

    /**
     * 账号
     */
    @ExcelProperty("账号")
    @ColumnWidth(20)
    private String account;


    /**
     * 昵称
     */
    @ExcelProperty("昵称")
    @ColumnWidth(15)
    private  String nickname;

    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    @ColumnWidth(15)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    @ColumnWidth(15)
    private String mailbox;

    /**
     * 密码
     */
    @ExcelProperty("密码")
    @ColumnWidth(10)
    private String password;

    /**
     * 头像路径
     */
    @ExcelProperty("头像路径")
    @ColumnWidth(70)
    private String headUrl;

    /**
     * 性别
     */
    @ExcelProperty("性别")
    @ColumnWidth(10)
    private Integer sex;

    /**
     * 生日
     */
    @ExcelProperty("生日")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(10)
    private Date birthday;

    /**
     * 简介
     */
    @ExcelProperty("简介")
    @ColumnWidth(40)
    private String introduction;

    /**
     * 拥有的小猪币的数量
     */
    @ExcelProperty("小猪币拥有量")
    @ColumnWidth(10)
    private Integer coinCnt;

    /**
     * 用户角色
     */
    @ExcelProperty("角色")
    @ColumnWidth(10)
    private Integer role;
    @ColumnWidth(10)
    @ExcelProperty("状态")
    private Integer isForbidden;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCoinCnt(Integer coinCnt) {
        this.coinCnt = coinCnt;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setIsForbidden(Integer isForbidden) {
        this.isForbidden = isForbidden;
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public String getMailbox() {
        return mailbox;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Integer getCoinCnt() {
        return coinCnt;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getIsForbidden() {
        return isForbidden;
    }
}
