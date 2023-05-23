package com.chandler.blog.service;


import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.User;

/**
* @author Chandler
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-04-30 21:43:22
*/
public interface BlogUserService{

	ResponseResult login(User user);

	ResponseResult logout();
}
