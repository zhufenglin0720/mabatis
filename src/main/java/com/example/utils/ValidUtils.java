package com.example.utils;

import com.example.enums.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author zfl
 * @create 2022/1/18 16:55
 * @description
 */
public class ValidUtils {

    /**
     * Assert not blank.
     * @param value the value
     * @param errorEnum the error enum
     */
    public static void assertNotBlank(String value, ErrorCodeEnum errorEnum) {
        if (StringUtils.isBlank(value)) {
            throw ExceptionUtils.create(errorEnum);
        }
    }

    /**
     * Assert not blank.
     *
     * @param value the value
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     */
    public static void assertNotBlank(String value, ErrorCodeEnum errorEnum, Object[] fillParameter) {
        if (StringUtils.isBlank(value)) {
            throw ExceptionUtils.create(errorEnum, fillParameter);
        }
    }

    /**
     * Assert not blank.
     * @param value the value
     * @param errorEnum the error enum
     */
    public static void assertBlank(String value, ErrorCodeEnum errorEnum) {
        if (StringUtils.isNotBlank(value)) {
            throw ExceptionUtils.create(errorEnum);
        }
    }

    /**
     * Assert blank.
     *
     * @param value the value
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     */
    public static void assertBlank(String value, ErrorCodeEnum errorEnum, Object[] fillParameter) {
        if (StringUtils.isNotBlank(value)) {
            throw ExceptionUtils.create(errorEnum, fillParameter);
        }
    }

    /**
     * Assert not null.
     *
     * @param value the value
     * @param errorEnum the e
     */
    public static void assertNotNull(Object value, ErrorCodeEnum errorEnum) {
        if (Objects.isNull(value)) {
            throw ExceptionUtils.create(errorEnum);
        }
    }

    /**
     * Assert not null.
     *
     * @param value the value
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     */
    public static void assertNotNull(Object value, ErrorCodeEnum errorEnum, String... fillParameter) {
        if (Objects.isNull(value)) {
            throw ExceptionUtils.create(errorEnum, Arrays.stream(fillParameter).toArray());
        }
    }

    /**
     * Assert not null.
     *
     * @param value the value
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     */
    public static void assertNotNull(Object value, ErrorCodeEnum errorEnum, Object[] fillParameter) {
        if (Objects.isNull(value)) {
            throw ExceptionUtils.create(errorEnum, fillParameter);
        }
    }

    /**
     * Assert null.
     *
     * @param value the value
     * @param errorEnum the e
     */
    public static void assertNull(Object value, ErrorCodeEnum errorEnum) {
        if (Objects.nonNull(value)) {
            throw ExceptionUtils.create(errorEnum);
        }
    }

    /**
     * Assert null.
     *
     * @param value the value
     * @param errorEnum the error enum
     * @param fillParameter the fill parameter
     */
    public static void assertNull(Object value, ErrorCodeEnum errorEnum, Object[] fillParameter) {
        if (Objects.nonNull(value)) {
            throw ExceptionUtils.create(errorEnum, fillParameter);
        }
    }
}
