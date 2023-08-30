package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.itheima.reggie.common
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/23 21:41
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.info("异常被捕获："+ex.getMessage());
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            return R.error(s[2] + "已存在");

        }
        return R.error("未知错误");
    }


    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        String message = ex.getMessage();
        log.info("异常被捕获："+ex.getMessage());
        return R.error(ex.getMessage());
    }
}
