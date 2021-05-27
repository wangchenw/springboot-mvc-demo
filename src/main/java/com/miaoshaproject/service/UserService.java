package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessExcption;
import com.miaoshaproject.service.model.UserModel;


public interface UserService {
      // 通过对象id获取对象
    UserModel getUserById(Integer id);

    void register (UserModel userModel) throws BusinessExcption;

    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessExcption;

}
