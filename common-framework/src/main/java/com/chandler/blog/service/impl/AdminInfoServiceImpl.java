package com.chandler.blog.service.impl;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.vo.AdminInfoVo;
import com.chandler.blog.entity.vo.MenuVo;
import com.chandler.blog.entity.vo.UserInfoVo;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.*;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	@Override
	public ResponseResult getInfo(String token) {
		if (!StringUtils.hasText(token))
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		// 解析出userId
		User user = SecurityUtils.getLoginUser().getUser();
		Long userId = user.getId();
		if (null == userId)
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		// 用户信息
		UserInfoVo userInfoVo = BeanCopyUtils.beanCopy(user, UserInfoVo.class);
		// 根据userId查询权限信息
		List<String> perms = menuService.getPermsByUserId(userId);
		// 根据userId查询角色信息
		List<String> roles = roleService.getRoleNameByUserId(userId);
		// 封装返回
		AdminInfoVo adminInfoVo = new AdminInfoVo(perms, roles, userInfoVo);
		return ResponseResult.okResult(adminInfoVo);
	}

	@Override
	public ResponseResult getRouters(String token) {
		if (!StringUtils.hasText(token))
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		// 解析出userId
		User user = SecurityUtils.getLoginUser().getUser();
		Long userId = user.getId();
		if (null == userId)
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		List<MenuVo> menus = menuService.getMenuVoTreeByUserId(userId);
		HashMap<String, List<MenuVo>> result = new HashMap<>();
		result.put("menus", menus);
		return ResponseResult.okResult(result);
	}
}
