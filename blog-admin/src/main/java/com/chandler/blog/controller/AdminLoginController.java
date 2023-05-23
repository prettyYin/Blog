package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.LoginUserDto;
import com.chandler.blog.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AdminLoginController {

	@Autowired
	AdminLoginService adminLoginService;

	@PostMapping("/login")
	public ResponseResult adminLogin(@RequestBody LoginUserDto userDto) {
		return adminLoginService.adminLogin(userDto);
	}

	@PostMapping("/logout")
	public ResponseResult adminLogout(@RequestHeader(value = "token") String token) {
		return adminLoginService.adminLogout(token);
	}
}
