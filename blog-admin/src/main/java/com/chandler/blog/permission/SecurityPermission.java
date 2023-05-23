package com.chandler.blog.permission;

import com.chandler.blog.service.MenuService;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("perms")
public class SecurityPermission {

	@Autowired
	MenuService menuService;

	/**
	 * 判断是否具有authority权限
	 * @param authority
	 * @return
	 */
	public boolean hasAuthority(String authority) {
		// 判断当前用户
		if (SecurityUtils.isAdmin()) {
			return true;
		}
		// 判断权限
		Long userId = SecurityUtils.getUserId();
		List<String> permissions = menuService.getPermsByUserId(userId);
		return permissions.contains(authority);
	}
}
