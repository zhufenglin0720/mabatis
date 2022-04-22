package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zfl
 * @create 2022/3/15 10:02
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface ClassDefineAnnotation {

    String description();

    String displayName();

    enum ClassType {
        /**
         * 公共修饰符
         */
        PUBLIC,
        /**
         * 私有
         */
        PRIVATE,
        /**
         * 同级包可访问
         */
        PROTECT
    }

    ClassType classType() default ClassType.PROTECT;

}
