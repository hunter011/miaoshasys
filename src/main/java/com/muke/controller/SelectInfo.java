package com.muke.controller;

import com.muke.common.JsonResult;
import com.muke.entity.User;
import com.muke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-03
 * Time:    11:49
 */
@Controller
@RequestMapping("/select")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class SelectInfo {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/findUserById")
    @ResponseBody
    public JsonResult findUserById(HttpSession session, Integer id) {


        User user = userService.findUserById(id);
        request.getSession().setAttribute("user",user);
        return new JsonResult(1,user);


    }

    @RequestMapping("/getSession")
    @ResponseBody
    public JsonResult getSession(HttpSession session) {
        User user2 = (User) request.getSession().getAttribute("user");
        return new JsonResult(1,user2);


    }

}
