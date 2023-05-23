package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.RoleMenu;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.dto.AddRoleDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.dto.UpdateRoleDto;
import com.chandler.blog.entity.vo.AdminRoleVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.entity.vo.RoleStatusDto;
import com.chandler.blog.mapper.RoleMapper;
import com.chandler.blog.service.MenuService;
import com.chandler.blog.service.RoleMenuService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author chandler
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	RoleMenuService roleMenuService;

	@Autowired
	MenuService menuService;

	@Override
	public List<String> getRoleNameByUserId(Long userId) {
		return roleMapper.getRoleNameByUserId(userId);
	}

	@Override
	public ResponseResult<PageVo> queryRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName)
				.eq(StringUtils.hasText(status), Role::getStatus, status);
		Page<Role> page = new Page<>(pageNum, pageSize);
		page(page, wrapper);
		List<Role> roles = page.getRecords();
		List<AdminRoleVo> adminRoleVos = BeanCopyUtils.beanCopyList(roles, AdminRoleVo.class);
		PageVo pageVo = new PageVo(adminRoleVos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Transactional
	@Override
	public ResponseResult addRole(AddRoleDto addRoleDto) {
		Role role = BeanCopyUtils.beanCopy(addRoleDto, Role.class);
		// 新增角色
		save(role);
		// 保存角色-菜单关联
		List<RoleMenu> roleMenuList = addRoleDto.getMenuIds()
				.stream()
				.map(menuId -> new RoleMenu(role.getId(), menuId))
				.collect(Collectors.toList());
		roleMenuService.saveBatch(roleMenuList);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<AdminRoleVo> echoRoleById(Long id) {
		Role role = getById(id);
		AdminRoleVo adminRoleVo = BeanCopyUtils.beanCopy(role, AdminRoleVo.class);
		return ResponseResult.okResult(adminRoleVo);
	}

	@Transactional
	@Override
	public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
		// 1.更新角色信息
		Role role = BeanCopyUtils.beanCopy(updateRoleDto, Role.class);
		updateById(role);
		// 2.更新角色-权限关联（先删除之前所有的关联，再更新所有的关联）
		// 删除
		Long roleId = updateRoleDto.getId();
		List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleId);
		menuIds.stream()
				.map(menuId -> new RoleMenu(roleId, menuId))
				.forEach(roleMenu -> roleMenuService.deleteByMultiId(roleMenu));
		// 更新
		List<RoleMenu> roleMenuList = updateRoleDto.getMenuIds()
				.stream()
				.map(menuId -> new RoleMenu(roleId, menuId))
				.collect(Collectors.toList());
		roleMenuService.saveOrUpdateBatchByMultiId(roleMenuList);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteRole(Long id) {
		LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
		wrapper.set(Role::getDelFlag, SystemConstants.DEL_FLAG_TRUE)
				.set(Role::getUpdateBy, SecurityUtils.getUserId())
				.eq(Role::getId, id);
		update(null, wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<Role> echoAllRole() {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
		List<Role> roles = list(wrapper);
		return ResponseResult.okResult(roles);
	}

	@Override
	public List<Role> getAllRoles() {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
		List<Role> roles = list(wrapper);
		return roles;
	}

	@Override
	public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
		Long userId = SecurityUtils.getUserId();
		LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Role::getId, roleStatusDto.getRoleId())
				.set(Role::getStatus, roleStatusDto.getStatus())
				.set(Role::getUpdateBy, userId); // 更新修改人
		update(null, wrapper);
		return ResponseResult.okResult();
	}

}

