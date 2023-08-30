package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: DishMapper
 * Package: com.itheima.reggie.mapper
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/25 20:41
 * @Version 1.0
 */
@Mapper
public interface DishMapper extends  BaseMapper<Dish> {
}
