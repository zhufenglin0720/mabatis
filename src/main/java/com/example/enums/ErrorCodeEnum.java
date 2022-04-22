package com.example.enums;

/**
 * @author zfl
 * @create 2022/1/18 16:46
 * @description
 */
public enum ErrorCodeEnum {

    /**
     * 返回成功标实
     */
    SUCCESS("200", "成功"),
    /**
     * 反射类型没有包名
     */
    REFLECT_TYPE_PACKAGE_NOT_EXIST("ERROR-0001","反射类型没有包名,类型:%s"),
    REFLECT_TYPE_PARENT_NOT_EXIST("ERROR-0002","反射类型没有继承类型,类型:%s"),

    USER_IS_LOGIN("ERROR-0003","该用户已在其他地方登录:%s");


    private String errorCode;

    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorCode + ":" + errorMessage;
    }

    public String toString(Object... fillParameter){
        return errorCode + ":" + String.format(errorMessage, fillParameter);
    }
}
