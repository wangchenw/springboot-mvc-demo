package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessExcption;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    //定义exceptionhandler处理未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    //状态码：200 OK
    //请求已成功，请求所希望的响应头或数据体将随此响应返回。出现此状态码是表示正常状态。
    //请求成功: 后端业务逻辑也可以出错
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception exception){
        //BusinessExcption EX = (BusinessExcption) exception;
        //HashMap<String, Object> responseData = new HashMap<>();
        //CommonReturnType type = new CommonReturnType();
        //responseData.put("errCode",EX.getErrCode());
        //responseData.put("errMsg",EX.getErrMsg());
        //type.setStatus("fail");
        //type.setData(responseData);
        //return type;
        HashMap<String, Object> responseData = new HashMap<>();
        if (exception instanceof BusinessExcption){
            BusinessExcption EX = (BusinessExcption) exception;
            responseData.put("errCode",EX.getErrCode());
            responseData.put("errMsg",EX.getErrMsg());
        }else {
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");

    }
}
