package com.itheima.reggie.common;

/**
 * ClassName: CustomException
 * Package: com.itheima.reggie.common
 * Description：自定义业务异常类
 *
 * @Author :zyp
 * @Create 2023/08/25 21:01
 * @Version 1.0
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
