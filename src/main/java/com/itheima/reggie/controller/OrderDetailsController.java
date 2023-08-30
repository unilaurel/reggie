package com.itheima.reggie.controller;

import com.itheima.reggie.service.OrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: OrderDetailsController
 * Package: com.itheima.reggie.controller
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/30 23:51
 * @Version 1.0
 */
@RestController
@Slf4j
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;
}
