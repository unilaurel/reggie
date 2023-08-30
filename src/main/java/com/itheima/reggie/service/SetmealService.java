package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * ClassName: SetmealService
 * Package: com.itheima.reggie.service
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/25 20:44
 * @Version 1.0
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveDto(SetmealDto setmealDto);


    public SetmealDto getWithdish(long id);

    public void updateWithDishes(SetmealDto setmealDto);

    public void deleteWithDish(List<Long> ids);
}
