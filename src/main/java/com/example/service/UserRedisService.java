package com.example.service;

import com.example.enums.ErrorCodeEnum;
import com.example.pojo.SystemUser;
import com.example.utils.ExceptionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zfl
 * @create 2022/4/18 15:01
 * @description
 */
@Service
public class UserRedisService {

    private final String SAVE_USER_KEY_PREFIX = "custom-user-key:";

    private final String USER_ONLINE_LIST_KEY_PREFIX = "custom-online-user-key:";

    private final int DEFAULT_SAVE_TIME = 600;

    private final RedisTemplate<String,Object> customRedisTemplate;

    private final Random random = new Random();

    public UserRedisService(RedisTemplate<String,Object> customRedisTemplate){
        this.customRedisTemplate = customRedisTemplate;
    }

    private String generateUserKey(Object id){
        return SAVE_USER_KEY_PREFIX + id;
    }

    /**
     * 避免大量热点key同时失效，发生缓存雪崩
     * @return
     */
    private int generateRandomTime(){
        return DEFAULT_SAVE_TIME + random.nextInt(10);
    }

    public SystemUser find(int id){
        return (SystemUser) customRedisTemplate.opsForValue().get(generateUserKey(id));
    }

    public SystemUser find(String userName){
        return (SystemUser) customRedisTemplate.opsForValue().get(generateUserKey(userName));
    }

    /**
     * 为空也缓存起来，避免缓存击穿
     */
    public void saveUser(int id, SystemUser user){
        //避免多个线程同时更新，需要使用分布式锁
        String key = generateUserKey(id);
        if(customRedisTemplate.hasKey(key)){
            //为空时新建上锁
            customRedisTemplate.opsForValue().setIfPresent(key,user,generateRandomTime(),TimeUnit.SECONDS);
        }else{
            customRedisTemplate.opsForValue().setIfAbsent(key,user,generateRandomTime(),TimeUnit.SECONDS);
        }
    }

    /**
     * 为空也缓存起来，避免缓存击穿
     */
    public void saveUser(String userName, SystemUser user){
        //避免多个线程同时更新，需要使用分布式锁
        String key = generateUserKey(userName);
        if(customRedisTemplate.hasKey(key)){
            //为空时新建上锁
            customRedisTemplate.opsForValue().setIfPresent(key,user,generateRandomTime(),TimeUnit.SECONDS);
        }else{
            customRedisTemplate.opsForValue().setIfAbsent(key,user,generateRandomTime(),TimeUnit.SECONDS);
        }
    }

    /**
     * 为空也缓存起来，避免缓存击穿
     */
    public void saveUserNameList(int id){
        //避免多个线程同时更新，需要使用分布式锁
        if(customRedisTemplate.hasKey(USER_ONLINE_LIST_KEY_PREFIX)){
            //为空时新建上锁
            if(!customRedisTemplate.opsForSet().isMember(USER_ONLINE_LIST_KEY_PREFIX,id)){
                customRedisTemplate.opsForSet().add(USER_ONLINE_LIST_KEY_PREFIX,id);
            }else{
                throw ExceptionUtils.create(ErrorCodeEnum.USER_IS_LOGIN,id);
            }
        }else{
            customRedisTemplate.opsForSet().add(USER_ONLINE_LIST_KEY_PREFIX,id);
        }
    }

}
