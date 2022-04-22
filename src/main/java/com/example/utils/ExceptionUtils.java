package com.example.utils;

import com.example.enums.ErrorCodeEnum;
import com.example.exception.ApplicationException;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zfl
 * @create 2022/1/18 16:45
 * @description
 */
public class ExceptionUtils {

    /**
     * Create application exception.
     *
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     * @return the application exception
     */
    public static ApplicationException create(ErrorCodeEnum errorEnum, Object... fillParameter) {
        return new ApplicationException(errorEnum,fillParameter);
    }

}
