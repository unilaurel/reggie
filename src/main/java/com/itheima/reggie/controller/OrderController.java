package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: OrderController
 * Package: com.itheima.reggie.controller
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/30 23:50
 * @Version 1.0
 */
@RequestMapping("/order")
@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
log.info("用户订单数据：{}",orders);
       ordersService.submit(orders);
        return R.success("用户下单成功");
    }
}
