package com.muke.service;

import com.muke.entity.User;
import com.muke.error.BusinessException;
import com.muke.service.model.UserModel;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-24
 * Time:    20:58
 */
public interface UserService {

    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    // telphone 是用户输入的电话
    // encryPassword 是用户加密后的密码
    UserModel volidateLogin(String telphone,String encrptPassword) throws BusinessException;

    public User findUserById(Integer id);
}
