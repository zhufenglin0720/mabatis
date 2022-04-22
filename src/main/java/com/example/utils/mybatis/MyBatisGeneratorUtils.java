package com.example.utils.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zfl
 * @create 2022/1/27 17:37
 * @description
 */
public class MyBatisGeneratorUtils {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        //如果这里出现空指针，直接写绝对路径即可。
        String genCfg = "D:\\ztsr\\TestDemo\\src\\main\\resources\\mybatis-generator.xml";
        File configFile = new File(genCfg);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = null;
        try {
            assert config != null;
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert myBatisGenerator != null;
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("install success");
    }
}
