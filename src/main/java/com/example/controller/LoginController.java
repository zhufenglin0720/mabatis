package com.example.controller;

import com.example.pojo.SystemUser;
import com.example.service.SystemUserMapper;
import com.example.service.UserRedisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfl
 * @create 2022/4/18 17:21
 * @description
 */
@RestController
public class LoginController {

    private final UserRedisService userRedisService;

    private final SystemUserMapper systemUserMapper;

    public LoginController(UserRedisService userRedisService, SystemUserMapper systemUserMapper) {
        this.userRedisService = userRedisService;
        this.systemUserMapper = systemUserMapper;
    }

    @RequestMapping("/user/login")
    public void login(@RequestParam int id) {
        SystemUser systemUser = userRedisService.find(id);
        if (systemUser == null) {
            systemUser = systemUserMapper.selectById(id);
            userRedisService.saveUser(id,systemUser);
        }
        userRedisService.saveUserNameList(id);
    }

}
