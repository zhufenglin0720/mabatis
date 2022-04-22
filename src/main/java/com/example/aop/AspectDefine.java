//package com.example.aop;
//
//import com.alibaba.fastjson.JSON;
//import com.example.annotation.ClassDefineAnnotation;
//import com.example.utils.ReflectionUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author zfl
// * @create 2022/3/14 11:38
// * @description
// */
//@Component
//@Asp
//public class AspectDefine {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
////    @Pointcut("execution(* com.example.service.impl.SystemUserService.*(..))")
////    public void point(){
////        System.out.println("定义切入点");
////    }
//
//    @Pointcut("@annotation(com.example.annotation.MethodDefineAnnotation)")
//    public void annotationClass(){
//        System.out.println("定义Class注解切入点");
//    }
//
////    @Pointcut("@annotation(com.example.annotation.MethodDefineAnnotation)")
////    public void annotationMethod(){
////        System.out.println("定义Method注解切入点");
////    }
////
////    @Pointcut("@annotation(com.example.annotation.AttrDefineAnnotation)")
////    public void annotationAttr(){
////        System.out.println("定义Attr注解切入点");
////    }
//
////    @Before("point()")
////    public void remarkBefore(JoinPoint joinPoint){
////        log(joinPoint);
////        System.out.println("调用SystemUserService方法之前记录一下");
////    }
//
//    private void log(JoinPoint joinPoint){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//        // 记录下请求内容
//        logger.info("################URL : " + request.getRequestURL().toString());
//        logger.info("################HTTP_METHOD : " + request.getMethod());
//        logger.info("################IP : " + request.getRemoteAddr());
//        logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));
//
//        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
//        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        //返回的是需要加强的目标类的对象
//        logger.info("################TARGET: " + joinPoint.getTarget());
//        //返回的是经过加强后的代理类的对象
//        logger.info("################THIS: " + joinPoint.getThis());
//    }
//
////    @After("point()")
////    public void remarkAfter(JoinPoint joinPoint){
////        log(joinPoint);
////        System.out.println("调用SystemUserService方法之后记录一下");
////    }
////
////    @AfterReturning("point()")
////    public void remarkAfterReturning(JoinPoint joinPoint){
////        log(joinPoint);
////        System.out.println("调用SystemUserService方法成功之后记录一下");
////    }
////
////    @AfterThrowing("point()")
////    public void remarkAfterThrowing(JoinPoint joinPoint){
////        log(joinPoint);
////        System.out.println("调用SystemUserService方法抛异常之后记录一下");
////    }
////
////    @Around("point()")
////    public Object remarkAround(ProceedingJoinPoint joinPoint){
////        Object obj = null;
////        try {
////            log(joinPoint);
////            System.out.println("remarkAround start");
////            long start = System.currentTimeMillis();
////            //有返回值
////            obj = joinPoint.proceed();
////            long end = System.currentTimeMillis();
////
////            System.out.println("remarkAround耗时：" + (end - start));
////            System.out.println("remarkAround end,obj=" + JSON.toJSONString(obj));
////        }catch (Throwable e){
////            System.out.println(e);
////        }
////        return obj;
////    }
//
//    @Around("annotationClass()")
//    public Object around(ProceedingJoinPoint joinPoint){
//        Object obj = null;
//        try {
//            log(joinPoint);
//            System.out.println("remarkAround start");
//            long start = System.currentTimeMillis();
//            //有返回值
//            obj = joinPoint.proceed();
//            //获取类型的注解
//            List<String> annotations = ReflectionUtils.getAnnotations(joinPoint.getTarget().getClass());
//            long end = System.currentTimeMillis();
//
//            System.out.println("remarkAround耗时：" + (end - start));
//            System.out.println("remarkAround end,obj=" + JSON.toJSONString(obj));
//        }catch (Throwable e){
//            System.out.println(e);
//        }
//        return obj;
//    }
//}
