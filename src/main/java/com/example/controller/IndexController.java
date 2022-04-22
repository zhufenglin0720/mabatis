package com.example.controller;

import com.example.ctx.ClientCtx;
import com.example.ctx.client.RequestClientInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfl
 * @create 2022/1/18 17:45
 * @description
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String test(){
        RequestClientInfo requestClientInfo = ClientCtx.clientInfo();
        return "/:" + requestClientInfo.toString();
    }

    @GetMapping("/index")
    public String index(){
        RequestClientInfo requestClientInfo = ClientCtx.clientInfo();
        return "index:" + requestClientInfo.toString();
    }
}
