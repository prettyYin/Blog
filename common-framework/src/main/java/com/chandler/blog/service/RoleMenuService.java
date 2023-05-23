package com.chandler.blog.service;

import com.chandler.blog.entity.RoleMenu;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author chandler
 */
public interface RoleMenuService extends IMppService<RoleMenu> {

	List<Long> getMenuIdsByRoleId(Long id);
}
