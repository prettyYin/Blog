package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.service.AdminInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台管理员信息接口")
public class AdminInfoController {

	@Autowired
	private AdminInfoService adminInfoService;

	/**
	 * 获取用户信息，包括权限和角色信息
	 * @param token
	 * @return
	 * @throws Exception
	 */

	@ApiOperation(value = "获取用户信息，包括权限和角色信息")
	@GetMapping("/getInfo")
	public ResponseResult getInfo(@RequestHeader(value = "token") String token) throws Exception {
		return adminInfoService.getInfo(token);
	}

	/**
	 * 获取用户所能访问的菜单数据，使得前端实现动态路由效果
	 * @param token
	 * @return
	 */
	@GetMapping("/getRouters")
	public ResponseResult getRouters(@RequestHeader(value = "token") String token) {
		return adminInfoService.getRouters(token);
	}
}
