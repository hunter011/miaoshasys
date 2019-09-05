package com.muke.service.impl;

import com.muke.dao.UserMapper;
import com.muke.dao.UserPasswordMapper;
import com.muke.entity.User;
import com.muke.entity.UserPassword;
import com.muke.error.BusinessException;
import com.muke.error.EnBusinessError;
import com.muke.service.UserService;
import com.muke.service.model.UserModel;
import com.muke.validator.ValidatorImpl;
import com.muke.validator.ValidatorResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-24
 * Time:    20:58
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserPasswordMapper userPasswordMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);

        if (user == null) {
            return null;
        }
        // 通过用户id获取用户加密的密码信息
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());
        return convertFromEntity(user,userPassword);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR);
        }
        // 导入commons-lang3jar包后用于验证
        // if(StringUtils.isEmpty(userModel.getName()) || userModel.getGender() == null
        //     || userModel.getAge() == null || StringUtils.isEmpty(userModel.getTelphone())) {
        //     throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR);
        // }

        // 校验用户输入合法性
        ValidatorResult validatorResult = validator.validate(userModel);
       if (validatorResult.isHasErrors()) {
           throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR,validatorResult.getErrMsg());
       }
        //    实现Model--》User
        User user = convertFromModel(userModel);
        try {
            userMapper.insertSelective(user);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR,"手机号已重复");
        }
        // Model-->UserPassword
        UserPassword userPassword = convertPasswordFromModel(userModel);
        userPassword.setUserId(user.getId());
        userPasswordMapper.insertSelective(userPassword);

    }

    /**
     * 将用户输入的账号和密码进行验证
     * 先通过账号验证用户是否存在，
     * 再通过获取的user对象获取用户id，根据id查询密码，
     * 最后将用户信息和密码信息转换为用户模型对象
     * @param telphone
     * @param encrptPassword
     * @return
     * @throws BusinessException
     */
    @Override
    public UserModel volidateLogin(String telphone,String encrptPassword) throws BusinessException {
    //    通过用户手机获取用户信息
        User user = userMapper.selectByTelphone(telphone);
        if (user == null) {
            throw new BusinessException(EnBusinessError.USER_LOGIN_FAIL);
        }

        UserPassword userPassword = userPasswordMapper.selectByPrimaryKey(user.getId());

        UserModel userModel = convertFromEntity(user, userPassword);

        //    比对用户信息加密的密码是否和传输的密码一致
        if (!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())) {
            throw new BusinessException(EnBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    @Override
    public User findUserById(Integer id) {
        User user = null;
        try {
             user = userMapper.findUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 将UserModel对象转换为User对象
     * @param userModel
     * @return
     */
    private User convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        return user;
    }

    /**
     * 将Model中的密码转化出来
     * @param userModel
     * @return
     */
    private UserPassword convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        // userPassword.setUserId(userModel.getId());
        return userPassword;
    }

    /**
     * 将用户基本信息和密码封装到一个类里
     * @param user
     * @param userPassword
     * @return
     */
    private UserModel convertFromEntity(User user, UserPassword userPassword) {
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user,userModel);
        if (userPassword != null) {
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }
}
