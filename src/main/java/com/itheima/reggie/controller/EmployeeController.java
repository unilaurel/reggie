package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: EmployeeController
 * Package: com.itheima.reggie.controller
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/21 23:05
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1 将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2. 根据用户名查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3. 如果没有查询到返回登陆失败的结果
        if (emp == null) {
            return R.error("登陆失败");
        }

        //4 密码比对，如果不一致返回登陆失败
        if (!password.equals(emp.getPassword())){
            return R.error("密码错误");
        }

        //5. 查看员工状态，如果为已禁用状态则返回员工已禁用的结果
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        //6 登陆成功，将员工id存入session并返回登陆成功的结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }
}