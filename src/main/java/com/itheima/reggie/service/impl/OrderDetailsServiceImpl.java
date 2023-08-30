package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.mapper.OrderDetailsMapper;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.OrderDetailsService;
import com.itheima.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * ClassName: OrdersServiceImpl
 * Package: com.itheima.reggie.service.impl
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/30 23:48
 * @Version 1.0
 */
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetail> implements OrderDetailsService {
}
