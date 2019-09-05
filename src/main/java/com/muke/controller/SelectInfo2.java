package com.muke.controller;

import com.muke.common.JsonResult;
import com.muke.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-03
 * Time:    14:04
 */
@Controller
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
@RequestMapping("/select")
public class SelectInfo2 {

    @RequestMapping("/getSessionValue")
    @ResponseBody
    public JsonResult getSessionValue(HttpSession session) {
        User user2 = (User) session.getAttribute("user");
        return new JsonResult(1,user2);

    }
}
