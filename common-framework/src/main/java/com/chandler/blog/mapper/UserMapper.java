package com.chandler.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chandler.blog.entity.User;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

/**
* @author Chandler
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @EntityUser
*/
public interface UserMapper extends MppBaseMapper<User> {


}
