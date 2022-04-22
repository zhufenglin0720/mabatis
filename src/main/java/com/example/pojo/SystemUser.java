package com.example.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *  TABLE null
 */
public class SystemUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 0 未知 1男 2女
     */
    private String gender;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 地址
     */
    private String address;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 
     */
    private Date createDate;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 
     *
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 
     *
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     * @return userName 
     *
     * 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 
     *
     * 
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 
     * @return phone 
     *
     * 
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone 
     *
     * 
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 邮箱
     * @return email 
     *
     * 
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 
     *
     * 
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 身份证号码
     * @return idCard 
     *
     * 
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 身份证号码
     * @param idCard 
     *
     * 
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * 0 未知 1男 2女
     * @return gender 
     *
     * 
     */
    public String getGender() {
        return gender;
    }

    /**
     * 0 未知 1男 2女
     * @param gender 
     *
     * 
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 爱好
     * @return hobby 
     *
     * 
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * 爱好
     * @param hobby 
     *
     * 
     */
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    /**
     * 地址
     * @return address 
     *
     * 
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 
     *
     * 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 显示名
     * @return displayName 
     *
     * 
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 显示名
     * @param displayName 
     *
     * 
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    /**
     * 真实姓名
     * @return realName 
     *
     * 
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 真实姓名
     * @param realName 
     *
     * 
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 
     * @return createDate 
     *
     * 
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 
     * @param createDate 
     *
     * 
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 备注
     * @return remark 
     *
     * 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 
     *
     * 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}