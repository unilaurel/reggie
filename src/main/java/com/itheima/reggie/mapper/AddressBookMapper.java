package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: AddressBookMapper
 * Package: com.itheima.reggie.mapper
 * Description：
 *
 * @Author :zyp
 * @Create 2023/08/29 20:28
 * @Version 1.0
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
