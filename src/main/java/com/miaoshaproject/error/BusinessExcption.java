package com.miaoshaproject.error;

public class BusinessExcption extends Exception implements CommonError{

    private CommonError commonError;


    public BusinessExcption(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接收自定义errMsg的方式构造业务异常（通过覆盖原本errMsg）
    public BusinessExcption(CommonError commonError,String errMsg) {
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
    public CommonError setErrMsg(String str) {
        this.commonError.setErrMsg(str);
        return this;
    }
}
