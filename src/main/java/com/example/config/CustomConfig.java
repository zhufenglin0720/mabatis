package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zfl
 * @create 2022/4/18 14:22
 * @description
 */
@ConfigurationProperties(prefix = "custom.zfl")
@Component
public class CustomConfig {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomConfig{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
