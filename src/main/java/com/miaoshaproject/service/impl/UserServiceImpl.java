package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessExcption;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userDOMapper获取到对应用户的dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO==null){
           return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessExcption {
        if (userModel == null) {
            throw new BusinessExcption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //if (StringUtils.isEmpty(userModel.getName())
        //        ||userModel.getGender()==null
        //        ||userModel.getAge()==null
        //        ||StringUtils.isEmpty(userModel.getTelphone())){
        //    throw new BusinessExcption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        //}
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessExcption(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //UserDO userDO = new UserDO();
        //userDO.setAge(userModel.getAge());
        //userDO.setGender(userModel.getGender());
        //userDO.setTelphone(userModel.getTelphone());
        //userDO.setName(userModel.getName());


        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessExcption(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已被注册！");
        }

        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        /*
        insertSelect 相较于 insert 命令
            insertSelect对于没有更新的字段  会让数据库使用默认
            insert对于没有更新的字段   会让数据库初始化为null
         */
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessExcption {
        //通过手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null){
            throw new BusinessExcption(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        //比对密码
        if (!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessExcption(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private UserDO convertFromModel(UserModel userModel){
        UserDO userDO = new UserDO();
        if (userModel == null){
            return null;
        }
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}