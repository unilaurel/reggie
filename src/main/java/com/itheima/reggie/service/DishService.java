package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;

import java.util.List;

/**
 * ClassName: DishService
 * Package: com.itheima.reggie.service
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/25 20:43
 * @Version 1.0
 */
public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作2张表，dish,dish_flavor

    public void saveWithFlavor(DishDto dishDto);

    //查询菜品，同时返回菜品和口味数据
    public DishDto getByIdWithFlavor(long id);

    //更新菜品，同时更新菜品和口味数据
    public boolean updateWithFlavor(DishDto dishDto);

    List<Dish> getListBycategory(Dish dish);
}
