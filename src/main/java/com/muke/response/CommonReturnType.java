package com.muke.response;

/**
 * Created by   Intellif Idea 2019.08
 * Author:  Wang Yun
 * Date:    2019-08-25
 * Time:    11:12
 */
public class CommonReturnType   {

    // 表明对应请求的返回处理结果  “success”或"fail"
    private String status;
    // 如status=success data返回前端需要的json格式数据
    // 若statu=fail data返回通用的错误码格式
    private Object data;

    /**
     * 定义一个通用的创建方法,
     * 传入一个参数默认返回success
     * @param result
     * @return
     */
    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result,"success");
    }

    /**
     * 方法重载
     *
     * @param result    返回的数据
     * @param status    返回的状态
     * @return
     */
    public static CommonReturnType create(Object result,String status) {

        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
