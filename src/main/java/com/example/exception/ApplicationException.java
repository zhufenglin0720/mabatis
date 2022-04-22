package com.example.exception;

import com.example.enums.ErrorCodeEnum;

/**
 * @author zfl
 * @create 2022/1/18 16:53
 * @description
 */
public class ApplicationException extends RuntimeException{

    /**
     * The Error enum.
     */
    private ErrorCodeEnum errorEnum;
    /**
     * The Fill parameter.
     */
    private Object[] fillParameter;

    public ErrorCodeEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorCodeEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

    public Object[] getFillParameter() {
        return fillParameter;
    }

    public void setFillParameter(Object[] fillParameter) {
        this.fillParameter = fillParameter;
    }

    /**
     * Instantiates a new Abstract basic runtime exception.
     * @param errorEnum the error enum
     */
    public ApplicationException(ErrorCodeEnum errorEnum) {
        super(errorEnum.getErrorMessage());
        this.errorEnum = errorEnum;
    }

    /**
     * Instantiates a new Abstract basic runtime exception.
     *
     * @param errorEnum the error enum
     * @param t the t
     */
    public ApplicationException(ErrorCodeEnum errorEnum, Throwable t) {
        super(errorEnum.getErrorMessage(), t);
        this.errorEnum = errorEnum;
    }

    public ApplicationException(ErrorCodeEnum errorEnum,Object[] fillParameter) {
        super(errorEnum.getErrorMessage());
        this.errorEnum = errorEnum;
        this.fillParameter = fillParameter;
    }

    /**
     * 填充errorMessage %s的数据
     *
     * @param fillParameter the fill parameter
     */
    public void addFillParameter(String... fillParameter) {
        this.fillParameter = fillParameter;
    }


    @Override
    public String getMessage() {
        if (errorEnum != null) {
            if (fillParameter != null && fillParameter.length > 0) {
                return errorEnum.toString(fillParameter);
            } else {
                return errorEnum.toString();
            }
        } else {
            return super.getMessage();
        }
    }
}
