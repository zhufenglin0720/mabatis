package com.example.service;

import com.example.pojo.SysGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author zfl
 * @create 2022/4/22 10:36
 * @description
 */
@Mapper
public class SysGoodsMapper {

    @Insert("insert into sys_goods(number,price,createDate) values (${number},${price},${createDate})")
    @Options(keyProperty = "number",keyColumn = "number")
    public void insert(SysGoods sysGoods){

    }

}
