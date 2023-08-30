package com.itheima.reggie.common;

/**
 * ClassName: BaseContext
 * Package: com.itheima.reggie.common
 * Description：基于threadlocal封装工具类，用于保存和获取当前登陆用户ID
 *
 * @Author :zyp
 * @Create 2023/08/24 23:37
 * @Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
