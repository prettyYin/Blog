package com.chandler.blog.service;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.LoginUserDto;

public interface AdminLoginService {
	ResponseResult adminLogin(LoginUserDto userDto);

	ResponseResult adminLogout(String token);
}
