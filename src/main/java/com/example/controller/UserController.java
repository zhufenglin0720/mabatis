package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.annotation.AttrDefineAnnotation;
import com.example.annotation.ClassDefineAnnotation;
import com.example.annotation.MethodDefineAnnotation;
import com.example.service.impl.SystemUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfl
 * @create 2022/3/14 14:10
 * @description
 */
@RestController
@ClassDefineAnnotation(description = "用户控制器",displayName = "用户控制器", classType = ClassDefineAnnotation.ClassType.PUBLIC)
public class UserController {

    @AttrDefineAnnotation(description = "用户属性",displayName = "用户属性")
    private final SystemUserService userService;


    public UserController(SystemUserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/findAll")
    @MethodDefineAnnotation(displayName = "用户方法",description = "用户方法")
    public String findAllUser(){
        return JSON.toJSONString(userService.selectAll());
    }

    @GetMapping("/user/findById/{id}")
    public String findUserById(@PathVariable Integer id){
        return JSON.toJSONString(userService.selectById(id));
    }

    @GetMapping("/user/findUserByUserName")
    public String findUserByIdAndUserName(@RequestParam String userName){
        return JSON.toJSONString(userService.selectByUsername(userName));
    }

    @GetMapping("/user/transaction/test")
    public void testTransaction(){
        userService.transactionA();
    }

    @GetMapping("/user/insetMillonsUser")
    public void insetMillonsUser(){
        userService.transactionA();
    }

}
