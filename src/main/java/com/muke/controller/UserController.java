package com.muke.controller;

import com.alibaba.druid.util.StringUtils;
import com.muke.controller.viewobject.UserVO;
import com.muke.error.BusinessException;
import com.muke.error.EnBusinessError;
import com.muke.response.CommonReturnType;
import com.muke.service.UserService;
import com.muke.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-24
 * Time:    20:52
 */
@Controller
@RequestMapping("/user")
// @CrossOrigin解决跨域问题，但是无法解决session共享
// 需要配置()的内容另加上在前端设置xhrFields授信后才可以实现session共享
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 实现用户登录
     * @param telphone
     * @param password
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/login")
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone, @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //    入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) || org.apache.commons.lang3.StringUtils.isEmpty(password)) {
            throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR);
        }
        //    用户登录服务
        UserModel userModel = userService.volidateLogin(telphone, this.EncodeByMd5(password));

        //将登录凭证加入到用户登录成功的session中
        request.getSession().setAttribute("IS_LOGIN", true);
        request.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);

    }

    /**
     * 实现用户注册功能
     * 用户注册时需要将输入的验证码和session中的验证码进行验证
     *
     * @param telphone
     * @param otpCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @param request
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") boolean gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password,
                                     HttpServletRequest request) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //    验证手机号和otpCode相符合
        //    从session中或取验证码和用户输入的进行比较
        String inSessionOtpCode = (String) request.getSession().getAttribute(telphone);

        if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EnBusinessError.PARAMETER_VALIDATTION_ERROR, "短信验证码不符合");
        }
        //    用户注册流程
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    /**
     * 将密码进行加密处理
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //     确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //    加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;

    }

    /**
     * 获取用户注册时要用到的验证码
     *
     * @param telphone
     * @return
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        //    需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        //    将OTP验证码同用户手机号关联,使用HTTPSession方式绑定用户optCede和手机号
        request.getSession().setAttribute(telphone, optCode);
        //    将OTP验证码通过短信渠道发送给用户，省略
        System.out.println("telphone= " + telphone + " & optCode=" + optCode);

        return CommonReturnType.create(null);
    }

    /**
     * 将用户所有信息转换为要展示前端是显示的对象，
     * 该对象保存的只是前端要展示的内容，不包括敏感资源。
     * @param id
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //    调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        // 若获取用户信息不存在
        if (userModel == null) {
            // userModel.setName("lisi");
            throw new BusinessException(EnBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);
        // 返回通用对象
        return CommonReturnType.create(userVO);

    }

    /**
     * 用于将信息转换为前端所要展示的信息，
     * 把该信息保存到一个对象中，不包含敏感资源
     * @param userModel
     * @return
     */
    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);

        return userVO;

    }


}
