package com.example.service.impl;

import com.example.pojo.SystemUser;
import com.example.service.SystemUserMapper;
import com.example.service.UserRedisService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zfl
 * @create 2022/3/14 14:03
 * @description
 */
@Service
public class SystemUserService {

    private final SystemUserMapper systemUserMapper;

    private final UserRedisService userRedisService;

    public SystemUserService(SystemUserMapper mapper,UserRedisService userRedisService){
        this.systemUserMapper = mapper;
        this.userRedisService = userRedisService;
    }

    public List<SystemUser> selectAll(){
        return systemUserMapper.selectAll();
    }

    public SystemUser selectById(Integer id){
        SystemUser systemUser = userRedisService.find(id);
        if(systemUser == null){
            systemUser = systemUserMapper.selectById(id);
            userRedisService.saveUser(id,systemUser);
        }
        return systemUser;
    }

    public SystemUser selectByUsername(String userName){
        return systemUserMapper.selectByUsername(userName);
    }

    /**
     *  PROPAGATION_REQUIRED        如果当前已经存在事务，那么加入该事务，如果不存在事务，创建一个事务，这是默认的传播属性值
     *          只存在一个共享的事务,当有任何异常发生的时候，所有操作回滚。
     *  PROPAGATION_SUPPORTS        H如果当前已经存在事务，那么加入该事务，否则创建一个所谓的空事务
     *  PROPAGATION_MANDATORY       当前必须存在一个事务，否则抛出异常。
     *  PROPAGATION_REQUIRES_NEW    如果当前存在事务，先把当前事务相关内容封装到一个实体，然后重新创建一个新事务，接受这个实体为参数，用于事务的恢复。
     *                                  更直白的说法就是暂停当前事务(当前无事务则不需要)，创建一个新事务。 针对这种情况，两个事务没有依赖关系，可以实现新事务回滚了，但外部事务继续执行。
     *  PROPAGATION.NOT_SUPPORTED   如果当前存在事务，挂起当前事务，然后新的方法在没有事务的环境中执行，没有spring事务的环境下，sql的提交完全依赖于 defaultAutoCommit属性值
     *  PROPAGATION_NEVER           如果当前存在事务，则抛出异常，否则在无事务环境上执行代码。
     *  PROPAGATION_NESTED          如果当前存在事务，则使用 SavePoint 技术把当前事务状态进行保存，然后底层共用一个连接，当NESTED内部出错的时候，
     *                                  自行回滚到 SavePoint这个状态，只要外部捕获到了异常，就可以继续进行外部的事务提交，而不会受到内嵌业务的干扰，
     *                                  但是，如果外部事务抛出了异常，整个大事务都会回滚。
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transactionA(){
        //修改1,如果当前方法没有事务，mapper默认会commit
        systemUserMapper.transactionA("13222222221","117@qq.com","1231",2,1);
        //修改1
        ((SystemUserService)AopContext.currentProxy()).transactionB();
        //报错
        int  i = 1 / 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void transactionB(){
        //修改1,如果当前方法没有事务，mapper默认会commit
        systemUserMapper.transactionA("13222222221","117@qq.com","1234",2,2);
    }


}
