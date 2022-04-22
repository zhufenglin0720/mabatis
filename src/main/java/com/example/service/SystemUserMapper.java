package com.example.service;

import com.example.pojo.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 168
 */
@Mapper
public interface SystemUserMapper {

    /**
     * 查询所有用户
     *
     * @return 列表
     * @description 查询所有用户
     */
    List<SystemUser> selectAll();

    /**
     * 通过主键ID查询用户
     *
     * @param id 用户主键ID
     * @return 用户
     * @description 通过主键ID查询用户
     */
    SystemUser selectById(Integer id);


    /**
     * 通过主键ID查询用户
     *
     * @param id       用户主键ID
     * @param userName
     * @return 用户
     * @description 通过主键ID查询用户
     */
    SystemUser selectByUsername(String userName);

    /**
     * 修改
     *
     * @param phone
     * @param email
     * @param idCard
     * @param gender
     */
    void transactionA(String phone, String email, String idCard, Integer gender, Integer id);
}
