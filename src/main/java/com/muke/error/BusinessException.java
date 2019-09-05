package com.muke.error;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-25
 * Time:    11:42
 */
// 包装类业务异常类实现
public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;

    // 直接接收EnBusinessError的参数拥有构造业务逻辑异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    // 接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);

    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
