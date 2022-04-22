package com.example.ctx;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author zfl
 * @create 2022/1/18 16:26
 * @description
 */
public class AbstractBaseModel implements Serializable {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
