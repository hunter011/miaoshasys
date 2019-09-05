package com.muke;

import com.muke.dao.UserMapper;
import com.muke.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.muke"})
@RestController
@MapperScan("com.muke.dao")
public class App {

    @Autowired(required = false)
    private UserMapper userMapper;

    @RequestMapping("/")
    public String home() {
        User user = userMapper.selectByPrimaryKey(1);
        if (user == null) {
            return "用户对象不存在";
        } else {

            return user.getName();
        }

    }



    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
