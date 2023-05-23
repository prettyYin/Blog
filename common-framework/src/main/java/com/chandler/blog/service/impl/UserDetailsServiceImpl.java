package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.entity.LoginUser;
import com.chandler.blog.entity.User;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.mapper.UserMapper;
import com.chandler.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	MenuService menuService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getUserName, username);
		User user = userMapper.selectOne(queryWrapper);
		// 用户不存在抛出异常
		Optional.ofNullable(user).orElseThrow(() -> new SystemException(AppHttpCodeEnum.NEED_LOGIN));
		// 权限封装
		List<String> permissions = menuService.getPermsByUserId(user.getId());
		return new LoginUser(user,permissions);
	}
}
