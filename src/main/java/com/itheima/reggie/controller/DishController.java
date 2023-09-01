package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName: DishController
 * Package: com.itheima.reggie.controller
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/25 23:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        System.out.println(dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("菜品新增成功");
    }




    @GetMapping("/page")
    public R<Page> getByPage(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //新建条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            //手动处理records属性
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据ID查询菜品的信息和口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);

//        dishService.updateById()
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        boolean flag = dishService.updateWithFlavor(dishDto);
//        //redis清理所有菜品数据
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);

        //精确清理这个菜品分类下的数据
        String key="dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);
        System.out.println("看看我执行了吗");
        return R.success(flag?"菜品修改成功":"菜品修改失败");
    }

//    /**
//     * 根据ID查询菜品的信息和口味信息
//     * @param dish
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Dish>> getListBycategory( Dish dish){
//        List<Dish> list = dishService.getListBycategory(dish);
//
////        dishService.updateById()
//        return R.success(list);
//    }


    /**
     * 根据ID查询菜品的信息和口味信息
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getListBycategory( Dish dish){
        List<DishDto> dtoList=null;
        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        //先从redis获取缓存数据

        dtoList= (List<DishDto>) redisTemplate.opsForValue().get(key);
        //如果存在，直接返回，无需查询数据库
        if (dtoList!=null){
            return R.success(dtoList);
        }
        //如果不存在，需要查询数据库，并将查询到的结果缓存到redis
        List<Dish> list = dishService.getListBycategory(dish);
        dtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());

        redisTemplate.opsForValue().set(key,dtoList,60, TimeUnit.MINUTES);
//        dishService.updateById()
        return R.success(dtoList);
    }
}
