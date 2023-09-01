package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: UserController
 * Package: com.itheima.reggie.controller
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/27 22:25
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

//    private Map<String,String> maps=new HashMap<>();

    /**
     * 移动端用户获取验证码
     * @param user
     * @param httpSession
     * @return
     */
    @PostMapping("/sendMsg")
    private R<String> sendMsg(@RequestBody User user, HttpSession httpSession){
        //获取手机号
        String phone = user.getPhone();
        System.out.println(phone);
        System.out.println(user);
        if (StringUtils.isNotEmpty(phone)) {
            //生成4位的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}",code);
            //调用阿里云提供的短信服务API完成短信发送
//            SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            //需要将生成的验证码保存到Session
//            httpSession.setAttribute(phone,code);

            //优化，将生成的验证码保存到redis中，并设置有效期为5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
//            maps.put(phone, code);
//            System.out.println(maps);
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map,HttpSession httpSession){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone");
        //获取验证码
        String code = map.get("code");
        String code1 = (String) redisTemplate.opsForValue().get(phone);
        //进行验证码比对
//        String code1 = httpSession.getAttribute(phone).toString();
        if(code!=null && code.equals(code1)){
            //如果能够比对成功，说明登陆成功
            //判断当前用户是否为新用户，如果是新用户则自动完成注册
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                user=new User();
                user.setPhone(phone);
                userService.save(user);
            }
            httpSession.setAttribute("user",user.getId());

            //如果用户登陆成功，删除redis缓存中的验证码
            redisTemplate.delete(phone);
            log.info("登陆成功");
            return R.success(user);

        }


        return R.error("登陆失败");
    }
}
