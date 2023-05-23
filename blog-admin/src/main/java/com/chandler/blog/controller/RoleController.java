package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.dto.AddRoleDto;
import com.chandler.blog.entity.dto.UpdateRoleDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.vo.AdminRoleVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.entity.vo.RoleStatusDto;
import com.chandler.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

	@Autowired
	RoleService roleService;

	/**
	 * 根据roleName和status模糊查询并分页显示角色列表
	 * @param pageNum
	 * @param pageSize
	 * @param roleName
	 * @param status
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> queryRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
		return roleService.queryRoleList(pageNum, pageSize, roleName, status);
	}

	/**
	 * 改变角色状态
	 * @param roleStatusDto
	 * @return
	 */
	@PutMapping("/changeStatus")
	public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto) {
		return roleService.changeStatus(roleStatusDto);
	}

	/**
	 * 新增角色
	 * @param addRoleDto
	 * @return
	 */
	@PostMapping
	public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
		return roleService.addRole(addRoleDto);
	}

	/**
	 * 回显角色，用于修改角色信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<AdminRoleVo> echoRoleById(@PathVariable("id") Long id) {
		return roleService.echoRoleById(id);
	}

	@PutMapping
	public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
		return roleService.updateRole(updateRoleDto);
	}

	/**
	 * 删除固定的某个角色（逻辑删除）
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseResult deleteRole(@PathVariable("id") Long id) {
		return roleService.deleteRole(id);
	}

	/**
	 * 回显所有状态正常、未被删除的角色，用于新增或修改用户接口时和角色的关联
	 * @return
	 */
	@GetMapping("/listAllRole")
	public ResponseResult<Role> echoAllRole() {
		return roleService.echoAllRole();
	}
}
