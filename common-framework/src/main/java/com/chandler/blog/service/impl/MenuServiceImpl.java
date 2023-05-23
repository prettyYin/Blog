package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Menu;
import com.chandler.blog.entity.RoleMenu;
import com.chandler.blog.entity.vo.AdminMenuTreeVo;
import com.chandler.blog.entity.vo.AdminMenuVo;
import com.chandler.blog.entity.vo.MenuVo;
import com.chandler.blog.entity.vo.RoleMenuTreeVo;
import com.chandler.blog.mapper.MenuMapper;
import com.chandler.blog.service.RoleMenuService;
import com.chandler.blog.service.UserRoleService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.MenuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author chandler
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

	@Autowired
	MenuMapper menuMapper;

	@Autowired
	RoleMenuService roleMenuService;

	@Autowired
	UserRoleService userRoleService;


	@Override
	public List<String> getPermsByUserId(Long userId) {
		// 如果用户为超级管理员，直接返回所有权限
		if (1L == userId && null != userId) {
			LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
			wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
			List<Menu> menus = list(wrapper);
			List<String> perms = menus.stream()
					.map(menu -> menu.getPerms())
					.collect(Collectors.toList());
			return perms;
		}
		// 否则返回对应userId的权限
		return menuMapper.getPermsByUserId(userId);
	}

	@Override
	public List<MenuVo> getMenuVoTreeByUserId(Long userId) {
		List<Menu> menus;
		// 如果用户为超级管理员，直接返回所有用户menu信息
		if (SecurityUtils.isAdmin()) {
			LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
			wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.CATALOGUE);
			wrapper.orderByAsc(Menu::getOrderNum);
			menus = list(wrapper);
		} else { // 否则查找userId对应的menus信息
			menus = menuMapper.getMenusByUserId(userId);
		}
		// 封装VO对象
		List<MenuVo> menuVos = BeanCopyUtils.beanCopyList(menus, MenuVo.class);
		// 构造树形结构的menu
		menuVos = buildMenuTree(menuVos,0L);
		return menuVos;
	}

	@Override
	public ResponseResult<AdminMenuVo> queryMenuList(String status, String menuName) {
		LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StringUtils.hasText(status), Menu::getStatus, status)
				.eq(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
		List<Menu> menus = list(wrapper);
		List<AdminMenuVo> adminMenuVos = BeanCopyUtils.beanCopyList(menus, AdminMenuVo.class);
		return ResponseResult.okResult(adminMenuVos);
	}

	@Transactional
	@Override
	public ResponseResult addMenu(Menu menu) {
		// 1.保存到menu表
		Long userId = SecurityUtils.getUserId();
		save(menu);
		// 2.保存menu_role表的关联
		// 获取当前用户的角色信息
		Long roleId = userRoleService.getRoleIdByUserId(userId);
		// 保存角色-菜单关联
		roleMenuService.save(new RoleMenu(roleId, menu.getId()));
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult echoMenuById(Long id) {
		Menu menu = getBaseMapper().selectById(id);
		AdminMenuVo adminMenuVo = BeanCopyUtils.beanCopy(menu, AdminMenuVo.class);
		return ResponseResult.okResult(adminMenuVo);
	}

	@Override
	public ResponseResult updateMenu(Menu menu) {
		if (menu.getParentId().equals(menu.getId())) { // 父级菜单是自己时，拦截异常操作
			return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,
					"修改菜单'" + menu.getMenuName() +  "'失败，上级菜单不能选择自己");
		}
		updateById(menu);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteMenu(Long menuId) {
		// 1.如果有子菜单，删除失败
		LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Menu::getDelFlag, SystemConstants.DEL_FLAG_FLASE);
		List<Menu> menus = list(queryWrapper);// 查找所有未被删除的菜单信息
		menus = menus.stream() // 查找所有的菜单信息的parentId是否有和被修改的menuId一致
				.filter(menu -> menu.getParentId().equals(menuId))
				.collect(Collectors.toList());
		if (!menus.isEmpty()) {
			return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "存在子菜单不允许删除");
		}
		// 2.反之，逻辑删除该菜单
		LambdaUpdateWrapper<Menu> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(Menu::getId, menuId)
				.set(Menu::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		update(null,updateWrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<AdminMenuTreeVo> treeSelect() {
		List<AdminMenuTreeVo> adminMenuTreeVos = getAdminMenuTreeVos();
		return ResponseResult.okResult(adminMenuTreeVos);
	}

	@Override
	public ResponseResult<RoleMenuTreeVo> roleMenuTreeselect(Long id) {
		List<AdminMenuTreeVo> adminMenuTreeVos = getAdminMenuTreeVos();
		List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(id);
		RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo(adminMenuTreeVos, menuIds);
		return ResponseResult.okResult(roleMenuTreeVo);
	}

	/**
	 * 获取菜单子树
	 * @return
	 */
	protected List<AdminMenuTreeVo> getAdminMenuTreeVos() {
		Long userId = SecurityUtils.getUserId();
		List<Menu> menus;
		if (SecurityUtils.isAdmin()) {
			LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
			wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.CATALOGUE);
			wrapper.orderByAsc(Menu::getOrderNum);
			menus = list(wrapper);
		} else {
			menus = getBaseMapper().getMenusByUserId(userId);
		}
		List<AdminMenuTreeVo> adminMenuTreeVos = menus.stream()
				.map(menu -> new AdminMenuTreeVo(null, menu.getId(), menu.getMenuName(), menu.getParentId()))
				.collect(Collectors.toList());
		// 构建子树
		adminMenuTreeVos = buildMenuSelectTree(adminMenuTreeVos, 0L);
		return adminMenuTreeVos;
	}
	/**
	 * 构建选择树
	 * @param adminMenuTreeVos
	 * @param parentId
	 * @return
	 */
	private List<AdminMenuTreeVo> buildMenuSelectTree(List<AdminMenuTreeVo> adminMenuTreeVos, Long parentId) {
		return adminMenuTreeVos.stream()
				.filter(adminMenuTreeVo -> adminMenuTreeVo.getParentId().equals(parentId)) // 寻找根节点
				.map(adminMenuTreeVo ->
						adminMenuTreeVo.setChildren(getChildren(adminMenuTreeVo, adminMenuTreeVos))) // 赋值子树
				.collect(Collectors.toList());
	}

	/**
	 * adminMenuTreeVos中查找adminMenuTreeVo的子数组
	 * @param adminMenuTreeVo
	 * @param adminMenuTreeVos
	 * @return
	 */
	private List<AdminMenuTreeVo> getChildren(AdminMenuTreeVo adminMenuTreeVo, List<AdminMenuTreeVo> adminMenuTreeVos) {
		List<AdminMenuTreeVo> children = adminMenuTreeVos
				.stream()
				.filter(m -> m.getParentId().equals(adminMenuTreeVo.getId()))
				// 递归设置根节点的子节点
				.map(m -> m.setChildren(getChildren(m, adminMenuTreeVos)))
				.collect(Collectors.toList());
		return children;
	}

	/**
	 * 根据 parentId 构建子数组
	 * @param menuVos
	 * @param parentId
	 * @return
	 */
	private List<MenuVo> buildMenuTree(List<MenuVo> menuVos, Long parentId) {
		List<MenuVo> menuTrees = menuVos
				.stream()
				// 寻找根节点
				.filter(menuVo -> menuVo.getParentId().equals(parentId))
				// 设置所有根节点的子节点
				.map(menuVo -> menuVo.setChildren(getChildren(menuVo, menuVos)))
				.collect(Collectors.toList());
		return menuTrees;
	}

	/**
	 * menuVos中查找menuVo的子数组
	 * @param menuVo
	 * @param menuVos
	 * @return
	 */
	private List<MenuVo> getChildren(MenuVo menuVo, List<MenuVo> menuVos) {
		List<MenuVo> children = menuVos
				.stream()
				.filter(m -> m.getParentId().equals(menuVo.getId()))
				// 递归设置根节点的子节点
				.map(m -> m.setChildren(getChildren(m, menuVos)))
				.collect(Collectors.toList());
		return children;
	}
}

