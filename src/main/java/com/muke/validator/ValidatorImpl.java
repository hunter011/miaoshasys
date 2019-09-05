package com.muke.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-01
 * Time:    14:25
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;
    //实现校验方法并返回校验结果
    public ValidatorResult validate(Object bean) {
        ValidatorResult result = new ValidatorResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
        //    有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolationt->{
                String errMsg = constraintViolationt.getMessage();
                String propertyName = constraintViolationt.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errMsg);

            });
        }
        return result;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
