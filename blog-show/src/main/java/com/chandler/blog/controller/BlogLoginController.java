package com.chandler.blog.controller;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.LoginUserDto;
import com.chandler.blog.entity.User;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.BlogUserService;
import com.chandler.blog.util.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户登录",description = "用户登录相关接口")
@RestController
public class BlogLoginController {

	@Autowired
	BlogUserService blogUserService;


	@ApiOperation(value = "登录接口")
	@PostMapping("/login")
	public ResponseResult login(@RequestBody LoginUserDto userDto) {
		if (!StringUtils.hasText(userDto.getUserName())) {
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		}
		User user = BeanCopyUtils.beanCopy(userDto, User.class);
		return blogUserService.login(user);
	}

	@ApiOperation(value = "登出接口")
	@PostMapping("/logout")
	public ResponseResult logout() {
		return blogUserService.logout();
	}
}
