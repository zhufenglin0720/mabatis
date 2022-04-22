package com.example.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author zfl
 * @create 2022/3/18 11:36
 * @description
 */
@WebFilter(filterName = "druidWebStatFilter",urlPatterns = "/*",
    initParams = {
            //排除一些不必要的url
            @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),
            //缺省sessionStatMaxCount是1000个
            @WebInitParam(name = "sessionStatMaxCount", value = "1000"),
            //session统计功能
            @WebInitParam(name = "sessionStatEnable", value = "false"),
            //配置principalSessionName，使得druid能够知道当前的session的用户是谁
            @WebInitParam(name = "principalSessionName", value = "xxx.user"),
            //如果你的user信息保存在cookie中，你可以配置principalCookieName，使得druid知道当前的user是谁
            @WebInitParam(name = "principalCookieName", value = "xxx.user"),
            //druid 0.2.7版本开始支持profile，配置profileEnable能够监控单个url调用的sql列表
            @WebInitParam(name = "profileEnable", value = "true")
    }
)
public class DruidStatFilter extends WebStatFilter {
}
