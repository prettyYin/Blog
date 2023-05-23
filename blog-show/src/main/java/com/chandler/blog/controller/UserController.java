package com.chandler.blog.controller;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.annotation.LogAnnotation;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.User;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/userInfo")
	@LogAnnotation(businessName = "查询用户信息")
	public ResponseResult userInfo(@RequestHeader(value = "token", required = true) String token) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		return userService.userInfo(token);
	}

	@PutMapping("/userInfo")
	@LogAnnotation(businessName = "更新用户信息")
	public ResponseResult updateUserInfo(@RequestHeader(value = "token", required = true) String token,
										 @RequestBody User user) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		return userService.updateUserInfo(token, user);
	}

	@PostMapping("/register")
	@LogAnnotation(businessName = "用户注册")
	public ResponseResult register(@RequestBody User user) {
		return userService.register(user);
	}
}
