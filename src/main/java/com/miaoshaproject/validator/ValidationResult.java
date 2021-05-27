package com.miaoshaproject.validator;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//public class ValidationResult {
//
//    //validationresult 是程序与 validator校验结果的格式规范
//    //包含了两个属性
//    //1. 布尔型  是否报错
//    //2. map类型 报错信息
//
//
//    //校验结果是否有错
//    private boolean hasErrors;
//
//    //存放错误信息的map
//    private Map<String,String> errorMsgMap;
//
//    public boolean isHasErrors() {
//        return hasErrors;
//    }
//
//    public void setHasErrors(boolean hasErrors) {
//        this.hasErrors = hasErrors;
//    }
//
//    public Map<String, String> getErrorMsgMap() {
//        return errorMsgMap;
//    }
//
//    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
//        this.errorMsgMap = errorMsgMap;
//    }
//
//    //实现 通用的 通过格式化字符串信息获取错误结果的msg方法
//    public String getErrMsg(){
//        return StringUtils.join(errorMsgMap.values().toArray(), ",");
//    }
//}
public class ValidationResult {
    /**
     * 校验结果是否有错
     */

    private boolean hasErrors;

    /**
     * 存放错误信息的map
     */

    private Map<String, String> errorMsgMap = new HashMap<>();


    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 实现通用的通过格式化字符串信息获取错误结果的msg方法
     *
     * @return
     */
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}

