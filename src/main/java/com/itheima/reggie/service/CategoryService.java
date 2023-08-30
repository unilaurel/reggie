package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * ClassName: CatagoryService
 * Package: com.itheima.reggie.service
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/25 18:51
 * @Version 1.0
 */
public interface CategoryService extends IService<Category> {

    public void removeid(long id);

}
