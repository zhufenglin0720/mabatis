package com.example.ctx;

import com.alibaba.fastjson.JSON;
import com.example.ctx.client.RequestClientInfo;
import org.springframework.context.ApplicationContext;

/**
 * @author zfl
 * @create 2022/1/18 16:25
 * @description
 */
public class ClientCtx extends AbstractBaseModel{

    private static final ThreadLocal<ClientCtx> CLIENT_CTX_THREAD_LOCAL = new ThreadLocal<>();

    private RequestClientInfo requestClientInfo;

    public RequestClientInfo getRequestClientInfo() {
        return requestClientInfo;
    }

    public void setRequestClientInfo(RequestClientInfo requestClientInfo) {
        this.requestClientInfo = requestClientInfo;
    }

    public static void init(){
        ClientCtx ctx = new ClientCtx();
        CLIENT_CTX_THREAD_LOCAL.set(ctx);
    }

    public static void setCtx(ClientCtx ctx){
        CLIENT_CTX_THREAD_LOCAL.set(ctx);
    }

    public static void remove(){
        if(null != CLIENT_CTX_THREAD_LOCAL){
            CLIENT_CTX_THREAD_LOCAL.remove();
        }
    }

    public static ClientCtx get(){
        ClientCtx clientCtx = CLIENT_CTX_THREAD_LOCAL.get();
        if(null == clientCtx){
            init();
            return get();
        }
        return clientCtx;
    }

    public static RequestClientInfo clientInfo(){
        return get().getRequestClientInfo();
    }

    public static ClientCtx clone(ClientCtx ctx){
        return JSON.parseObject(JSON.toJSONString(ctx), ClientCtx.class);
    }

}
