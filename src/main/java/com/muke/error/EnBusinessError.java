package com.muke.error;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-25
 * Time:    11:32
 */
public enum EnBusinessError implements CommonError {

    // 通用错误类型
    PARAMETER_VALIDATTION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    // 20000开头的为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),

    USER_LOGIN_FAIL(20002,"用户手机号或密码错误"),
    ;

    EnBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
