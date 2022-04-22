package com.example.ctx.client;

import com.example.ctx.AbstractBaseModel;

/**
 * 客户端信息
 *
 * @author wujunbo
 * @date 2021/6/9 15:10
 */
public class RequestClientInfo extends AbstractBaseModel {
    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 客户端操作系统
     */
    private String os;

    /**
     * 客户端浏览器类型
     */
    private String browser;

    /**
     * 客户端IP地址
     */
    private String clientIp;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
