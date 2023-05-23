package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.entity.RoleMenu;
import com.chandler.blog.mapper.RoleMenuMapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.RoleMenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author chandler
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends MppServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

	@Override
	public List<Long> getMenuIdsByRoleId(Long id) {
		LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(RoleMenu::getRoleId, id);
		List<RoleMenu> roleMenus = list(wrapper);
		List<Long> menuIds = roleMenus.stream()
				.map(roleMenu -> roleMenu.getMenuId())
				.collect(Collectors.toList());
		return menuIds;
	}
}

