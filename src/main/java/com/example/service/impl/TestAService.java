package com.example.service.impl;

import com.example.pojo.SystemUser;
import com.example.service.SystemUserMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zfl
 * @create 2022/3/17 18:32
 * @description
 */
@Service
public class TestAService {

//    private final SystemUserMapper systemUserMapper;
//
//    public TestAService(SystemUserMapper mapper){
//        this.systemUserMapper = mapper;
//    }

    @Autowired
    private SystemUserMapper systemUserMapper;


    @Transactional(rollbackFor = Throwable.class)
    public void transactionB(){
        //修改
        systemUserMapper.transactionA("13222222222","117@qq.com","1231",1,1);
        SystemUser systemUser = ((TestAService) AopContext.currentProxy()).selectById(1);
        //抛错
        int j = 1 / 0;
    }

    @Transactional
    public SystemUser selectById(Integer id){
        return systemUserMapper.selectById(id);
    }
}
