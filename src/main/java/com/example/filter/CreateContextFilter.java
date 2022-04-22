package com.example.filter;

import com.example.ctx.ClientCtx;
import com.example.utils.RequestUtils;
import com.example.ctx.client.RequestClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zfl
 * @create 2022/1/18 14:10
 * @description
 */
@WebFilter(displayName = "构造request上下文",urlPatterns = "/*")
@Order(value = 0)
public class CreateContextFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("---执行打印语句：开始设置请求上下文---");
        //设置上下文
        ClientCtx.get().setRequestClientInfo(RequestUtils.clientInfo((HttpServletRequest) servletRequest));
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
